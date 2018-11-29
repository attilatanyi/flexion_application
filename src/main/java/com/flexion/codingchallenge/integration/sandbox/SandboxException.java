package com.flexion.codingchallenge.integration.sandbox;

/**
 * A custom exception for the SandboxIntegration, so that the client knows what implementation caused a problem.
 */
public class SandboxException extends RuntimeException {

    public SandboxException() {
    }

    public SandboxException(String message) {
        super(message);
    }

}
