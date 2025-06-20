package Utils;

import base.BaseTest;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ExtentReportManager implements ITestListener {

    private static ExtentReports report;
    private static ExtentSparkReporter sparkReporter;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final Map<String, ExtentTest> classLevelTests = new ConcurrentHashMap<>();
    private static String reportName;

    @Override
    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm").format(new Date());
        String testName = context.getName();
        reportName = testName + "_test_Report_" + timeStamp + ".html" ;

        sparkReporter = new ExtentSparkReporter("reports/" + reportName);
        sparkReporter.config().setDocumentTitle("SKY MD Report");
        sparkReporter.config().setReportName(context.getName());
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimelineEnabled(true);

        report = new ExtentReports();
        report.attachReporter(sparkReporter);
        report.setSystemInfo("Application", "SKY MD");
        report.setSystemInfo("Tester Name", "Aravind");
        report.setSystemInfo("Environment", "Staging");

        report.setSystemInfo("Browser", context.getCurrentXmlTest().getParameter("browser"));
       // report.setSystemInfo("OS", context.getCurrentXmlTest().getParameter("os"));

        List<String> groups = context.getCurrentXmlTest().getIncludedGroups();
        if (groups != null && !groups.isEmpty()) {
            report.setSystemInfo("Groups", String.join(", ", groups));
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create a class-level node (parent) and method-level node (child)
        String className = result.getTestClass().getRealClass().getSimpleName(); // get class name only
        ExtentTest parent = classLevelTests.computeIfAbsent(className, k -> report.createTest(className)); // create class node only once
        ExtentTest child = parent.createNode(result.getMethod().getMethodName()); // create test method node under class
        extentTest.set(child); // set thread-safe child node
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getName() + " has passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ExtentTest test = extentTest.get();
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getName() + " has failed");
        test.log(Status.INFO, result.getThrowable().getMessage());

        String className = result.getTestClass().getRealClass().getSimpleName().substring(0,8);
        String methodName = result.getMethod().getMethodName();
        String screenshotName = "Fail" + "_" + className + "_" + methodName;

        try {
            String imgPath = BaseTest.captureScreenshot(screenshotName);
            test.addScreenCaptureFromPath(imgPath);
        } catch (Exception e) {
            test.warning("Screenshot could not be attached: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + " was skipped");
        if (result.getThrowable() != null) {
            test.log(Status.INFO, result.getThrowable().getMessage());
        } else {
            test.log(Status.INFO, "Test was skipped without a specific reason.");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        report.flush();
    }

    // Access ExtentTest object in your test classes
    public static ExtentTest getTest() {
        return extentTest.get();
    }
}
