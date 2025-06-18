package Utils;
import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import base.BaseTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentReportManager implements ITestListener{
    public ExtentSparkReporter esr;
    public ExtentReports report;
    public ExtentTest test;
    public String reportName;
    public void onStart(ITestContext context) {

        String timeStamp  = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reportName = "test_Report"+timeStamp+".html";

        esr = new ExtentSparkReporter(".\\reports\\"+reportName);
        esr.config().setDocumentTitle("Opencart Report");
        esr.config().setReportName(context.getName());
        esr.config().setTheme(Theme.DARK);

        report = new ExtentReports();
        report.attachReporter(esr);
        report.setSystemInfo("Application", "Opencart");
        report.setSystemInfo("Tester Name", "Aravind");
        report.setSystemInfo("Environment", "QA");
        report.setSystemInfo("Browser", context.getCurrentXmlTest().getParameter("browser"));
        report.setSystemInfo("OS", context.getCurrentXmlTest().getParameter("os"));
        List<String> groups = context.getCurrentXmlTest().getIncludedGroups();
        for(String group:groups) {
            //if we didn't mention any group name this if condition will fail
            if(!group.isEmpty())
                report.setSystemInfo("Groups", group.toString());
        }

    }
    public void onTestSuccess(ITestResult result) {
        test = report.createTest(result.getClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getName()+"is passed");
    }
    public void onTestFailure(ITestResult result) {
        test = report.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getName()+"is failed");
        test.log(Status.INFO, result.getThrowable().getMessage());
        try {
            String imgPath = new BaseTest().captureScreenshot(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void onTestSkipped(ITestResult result) {
        test = report.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName()+"is skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext context) {
        report.flush();

        // this will automatically opens the extent report after test execution completion
        String PathOfextentreport = ".\\reports\\"+reportName;
        File file = new File(PathOfextentreport);
        try {
            Desktop.getDesktop().browse(file.toURI());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}