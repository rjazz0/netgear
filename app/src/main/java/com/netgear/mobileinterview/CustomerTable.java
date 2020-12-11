package com.netgear.mobileinterview;

import java.util.HashMap;
import java.util.Map;

public class CustomerTable {

    private static CustomerTable instance;

    int nextCustomerId = 0;

    public Map<Integer, Customer> customers = new HashMap<>();

    public Customer createCustomer(String firstName, String lastName) {
        Customer customer = new Customer(nextCustomerId, firstName, lastName);
        nextCustomerId++;
        return customer;
    }

    public Customer getCustomer(int id) {
        return customers.get(id);
    }

    public void removeCustomer(int custId) {
        customers.remove(custId);
    }

    public void updateCustomer(Customer customer) {
        customers.put(customer.customerId, customer);
    }

    void reset() {
        nextCustomerId = 0;
        customers.clear();
    }

    public static CustomerTable getInstance() {
        if (CustomerTable.instance == null) {
            instance = new CustomerTable();
        }
        return instance;
    }
}
