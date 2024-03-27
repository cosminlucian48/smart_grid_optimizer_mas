package dreamsoftware.smartgridoptimizer.agents.impl;

import java.io.File;
import java.io.InputStream;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j256.simplejmx.common.JmxAttributeFieldInfo;
import com.j256.simplejmx.common.JmxAttributeMethodInfo;
import com.j256.simplejmx.common.JmxOperationInfo;
import com.j256.simplejmx.common.JmxResourceInfo;
import jade.content.ContentElement;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import dreamsoftware.smartgridoptimizer.agents.PublishSubscribeAgent;
import dreamsoftware.smartgridoptimizer.ontology.predicates.IterationStatus;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IReportVisitor;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class ReportAgent extends PublishSubscribeAgent implements IReportVisitor {
	
	private final Logger logger = LoggerFactory.getLogger(ReportAgent.class);

	@Serial
	private static final long serialVersionUID = 1L;

	public final static String AGENT_NAME = "REPORT_AGENT";
	public final static String AGENT_DESCRIPTION = "Agent responsible to report the status of system";
	// Report Formats Enum
	public enum ReportFormatEnum { PDF_FORMAT, XLS_FORMAT }
	// Generate report interval
	public static Integer GENERATE_REPORT_INTERVAL = 60000;
	// Folder to save the reports.
	private String reportRootFolder = System.getProperty("user.home") + "/reports";
	// Report's format
	private ReportFormatEnum reportFormat = ReportFormatEnum.PDF_FORMAT;
	
	private List<IterationStatus> iterationStatusList = new ArrayList<IterationStatus>();
	
	
	@Override
	protected void setup() {
		super.setup();
		this.addBehaviour(new GenerateReportBehaviour(this, GENERATE_REPORT_INTERVAL));
	}

	/**
	 * Register your services in the DF so that other agents can discover them.
	 */
	@Override
	protected void onRegisterServices(DFAgentDescription description) {
		ServiceDescription service = new ServiceDescription();
        service.setType(ENERGY);
        service.setName(GENERATE_REPORTS);
        description.addServices(service);
	}
	
	/**
	 * Services to which you want to subscribe
	 */
	@Override
	protected String[] getServicesToSubscribe() {
		return new String [] { REPORT_ITERATION_STATUS }; 
	}
	
	/**
	 * Notifies the agent the content received by the subscription.
	 */
	@Override
	protected void onNotify(ContentElement ce) {
		@SuppressWarnings("unchecked")
		IVisitable<IReportVisitor> messageVisitable = (IVisitable<IReportVisitor>)ce;
		messageVisitable.accept(this);
	}
	

	public void visit(IterationStatus iterationStatus) {
		iterationStatusList.add(iterationStatus);
	}
	
	@Override
	protected void onCreateMBean(JmxResourceInfo resourceInfo, List<JmxAttributeFieldInfo> attributeFieldInfoList,
								 List<JmxAttributeMethodInfo> attributeMethodInfoList, List<JmxOperationInfo> operationInfoList) {
		
		resourceInfo.setJmxDomainName(AGENT_NAME);
		resourceInfo.setJmxBeanName(this.getAID().getLocalName());
		resourceInfo.setJmxDescription(AGENT_DESCRIPTION);
		
		attributeFieldInfoList.add(new JmxAttributeFieldInfo("GENERATE_INFORM_INTERVAL", true, true, "Interval for generate report"));
		
		// Report's Folder
		attributeFieldInfoList.add(new JmxAttributeFieldInfo("reportRootFolder", true, false, "Folder for reports"));
		
		// Report's format
		attributeMethodInfoList.add(new JmxAttributeMethodInfo("getReportFormatName", "Format for Reports"));
		
		// Jmx Operation to change the report's folder.
		operationInfoList.add(new JmxOperationInfo("changeReportsFolder",
				new String [] { "folder" }, new String [] { "New folder" }, JmxOperationInfo.OperationAction.ACTION,
                "Change report directory"));
		
		// Jmx Operation to change the report's format.
		operationInfoList.add(new JmxOperationInfo("changeReportFormat",
				new String [] { "format" }, new String [] { "New Format" }, JmxOperationInfo.OperationAction.ACTION,
		        "Change report's format"));
		
	}

	/**
	 * Jmx Operation for change the report folder.
	 * @param folder
	 * @return
	 */
	public String changeReportsFolder(String folder) {
		String message;
		File newFolder = new File(folder);
		if (!newFolder.exists()) {
			message = "Directory does not exist";
		} else if (newFolder.exists() && !newFolder.isDirectory()) {
			message = "Not a directory";
		} else {
			reportRootFolder = folder;
			message = "Directory changed successfully";
		}
		return message;
	}
	
	/**
	 * Jmx Operation for change the report format
	 * @param format
	 * @return
	 */
	public String changeReportFormat(String format) {
		String message;
		String newFormat = format.toUpperCase() + "_FORMAT";
		Boolean validFormat = false;
		for(ReportFormatEnum report: ReportFormatEnum.values()) {
			if(report.name().equals(newFormat)){
				validFormat = true;
				break;
			}
		}
		if(validFormat) {
			reportFormat = ReportFormatEnum.valueOf(newFormat);
			message = "Report format changed successfully";
		} else {
			message = "Invalid format, must be pdf or xls";
		}
		return message;
	}

	/**
	 * JMX getter for get the report format name
	 * @return
	 */
	public String getReportFormatName() {
		return reportFormat.name();
	}

	/**
	 * Behaviour for generate report from Mashes Status.
	 */
	public class GenerateReportBehaviour extends TickerBehaviour {
		
		private static final long serialVersionUID = 1L;

		public GenerateReportBehaviour(Agent a, long period) {
			super(a, period);
		}

		@Override
		protected void onTick() {
			
			if(iterationStatusList.size() > 0) {
				
				// config params
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("IterationStatusList", new JRBeanCollectionDataSource(iterationStatusList));
				parameters.put("loadFactor", PowerLoadAgent.LOAD_FACTOR);
				parameters.put("genFactor", PowerGenerateAgent.GEN_FACTOR);
				parameters.put("cellMaxBatLevel", BatteryAgent.CELL_MAX_BAT_LEVEL);
				
				try {
					
					// Load report
					InputStream jasperStream = getClass().getResourceAsStream("/jasperreports/mashes_iteration_status.jasper");
					
					// Fill the report with data
					JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
					jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
					JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
					
				
					// check if exist report folder
					File rootFolder = new File(reportRootFolder);
					if(!rootFolder.exists())
						rootFolder.mkdirs();
					
					Calendar ca = Calendar.getInstance();
					if(reportFormat.equals(ReportFormatEnum.PDF_FORMAT)) {
						JRPdfExporter pdfExporter = new JRPdfExporter();
				        pdfExporter.setExporterInput(new SimpleExporterInput(print));
				        File pdf = new File(reportRootFolder, "report_" + ca.getTimeInMillis() + ".pdf");
				        pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdf));
				        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
				        configuration.setCreatingBatchModeBookmarks(true);
				        configuration.setMetadataAuthor("Sergio Sánchez Sánchez");
				        pdfExporter.setConfiguration(configuration);
				        pdfExporter.exportReport();
					} else {
						JRXlsExporter xlsExporter = new JRXlsExporter();
				        xlsExporter.setExporterInput(new SimpleExporterInput(print));
				        File xls = new File(reportRootFolder, "report_" + ca.getTimeInMillis() + ".xls");
				        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xls));
				        SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
				        xlsReportConfiguration.setOnePagePerSheet(false);
				        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
				        xlsReportConfiguration.setDetectCellType(false);
				        xlsReportConfiguration.setWhitePageBackground(false);
				        xlsExporter.setConfiguration(xlsReportConfiguration);
				        xlsExporter.exportReport();
					}
					
					
				} catch (Exception e) {
					logger.error("Error al generar el reporte");
					e.printStackTrace();
				}
				
				// remove all iterations.
				iterationStatusList.clear();
				
			}
		}
	}
}
