package com.qa.listners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.resources.Logger;
import com.qa.utils.ScreenShotManager;

public class ScreenShotTaker implements ITestListener
{
	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ITestContext arg0) {
		
		// Create the log.
		Logger.createLogFile();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		ScreenShotManager screenShot = new ScreenShotManager();
		screenShot.takeContinuousScreenShot();
		
		Throwable cause = result.getThrowable();
		if(cause!=null)
		{
			Logger.log("FAIL:  "+ cause.getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestStart(ITestResult result) {
		// Set the path for screen-shot
		String methodName=result.getName().toString().trim();
		String className=result.getTestClass().getName();
		className = className.replace("com.ensimtest.tests.", "").trim();
		ScreenShotManager.ScreenShotConfig.setPath(className, methodName);
		
		Logger.setFilePath(className, methodName);
		
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	
}

