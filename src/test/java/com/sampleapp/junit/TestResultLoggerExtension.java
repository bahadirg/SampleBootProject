package com.sampleapp.junit;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

// TestWatcher provides testDisabled, testSuccessful, testAborted, testFailed methods for logging after test methods execution
// Test classes must extend this class for logging with "@ExtendWith(TestResultLoggerExtension.class)" 
public class TestResultLoggerExtension implements TestWatcher {

	//TestWatcher interface's testDisabled, testSuccessful, testAborted, testFailed methods implementation
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
    	System.out.println("Test Disabled: " + context.getDisplayName());
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
    	System.out.println("Test Successful: " + context.getDisplayName());
    }  
    
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
    	System.out.println("Test Aborted: " + context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
    	System.out.println("Test Failed: " + context.getDisplayName());
    }
}
