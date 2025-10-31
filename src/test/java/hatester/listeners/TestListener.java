package hatester.listeners;

import hatester.helpers.CaptureHelper;
import hatester.helpers.SystemHelper;
import hatester.utils.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static int count_total = 0;
    private static int count_passed = 0;
    private static int count_failed = 0;
    private static int count_skipped = 0;

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    @Override
    public void onStart(ITestContext result) {
        LogUtils.info("Setup môi trường onStart: " + result.getStartDate());  //thời gian bắt đầu
        //Initialize report
        //Connect to database
        //Call API get Token
    }

    @Override
    public void onFinish(ITestContext result) {
        LogUtils.info("Kết thúc bộ test: " + result.getEndDate()); //thời gian kết thúc
        LogUtils.info("Total testcase: " + count_total);
        LogUtils.info("Total testcase pass: " + count_passed);
        LogUtils.info("Total testcase fail: " + count_failed);
        LogUtils.info("Total testcase skip: " + count_skipped);
        //Generate report
        //Kết thúc và thực thi Extents Report
//        ExtentReportManager.getExtentReports().flush();
        //Send email
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("Test Started: " + result.getName());
        count_total++;
        //Write log to file
        CaptureHelper.startRecord(result.getName());
        //Bắt đầu ghi 1 TCs mới vào Extent Report
//        ExtentTestManager.saveToReport(getTestName(result), getTestDescription(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("Test case " + result.getName() + " is passed.");
//        LogUtils.info("==> Status: " + result.getStatus());
        count_passed++;
        //Write log to file
        //Write status to report

        //Extent Report
//        ExtentTestManager.logMessage(Status.PASS, result.getName() + " is passed.");

        CaptureHelper.stopRecord();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.error("Test case " + result.getName() + " is failed.");
//        LogUtils.info("==> Status: " + result.getStatus());
        count_failed++;
        LogUtils.error("==> Reason: " + result.getThrowable()); //Lấy lý do lỗi
        CaptureHelper.takeScreenshot(result.getName() + "_" + SystemHelper.getDatetimeNowFormat()); //Lấy tên TCs làm tên hình ảnh
        //Create ticket on Jira
        //Write log to file
        //Write status to report

        //Extent Report
//        ExtentTestManager.addScreenshot(result.getName());
//        ExtentTestManager.logMessage(Status.FAIL, result.getThrowable().toString());
//        ExtentTestManager.logMessage(Status.FAIL, result.getName() + " is failed.");

        CaptureHelper.stopRecord();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("Test case " + result.getName() + " is skipped.");
//        LogUtils.info("==> Status: " + result.getStatus());
        count_skipped++;
        //Write log to file
        //Write status to report

        //Extent Report
//        ExtentTestManager.logMessage(Status.SKIP, result.getThrowable().toString());

        CaptureHelper.stopRecord();
    }
}
