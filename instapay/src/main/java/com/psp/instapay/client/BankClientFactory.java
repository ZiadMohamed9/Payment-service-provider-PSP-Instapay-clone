package com.psp.instapay.client;

import com.psp.instapay.client.banks.CIBClient;
import com.psp.instapay.client.banks.NBEClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating and managing bank client instances.
 * This class provides a way to retrieve the appropriate BankClient implementation
 * based on the bank name.
 */
@Component
public class BankClientFactory {
    private final Map<String, BankClient> clients;

    /**
     * Constructor for BankClientFactory.
     * Initializes the factory with specific bank client implementations.
     *
     * @param cibClient the CIBClient implementation
     * @param nbeClient the NBEClient implementation
     */
    public BankClientFactory(CIBClient cibClient, NBEClient nbeClient) {
        clients = new HashMap<>();
        clients.put("CIB", cibClient);
        clients.put("NBE", nbeClient);
    }

    /**
     * Retrieves the BankClient implementation for the specified bank name.
     *
     * @param bankName the name of the bank (e.g., "CIB", "NBE")
     * @return the corresponding BankClient implementation, or null if not found
     */
    public BankClient getBankClient(String bankName) {
        return clients.get(bankName);
    }
}