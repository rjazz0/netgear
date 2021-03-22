package com.netgear.mobileinterview;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class MobileInterviewDebugTests {

    @Before
    public void setup() {
        CustomerTable.getInstance().reset();
        OrderTable.getInstance().reset();
        PurchasableItems.getInstance().reset();
    }

    @Test
    public void testCustomer() {
        Customer customer0 = new Customer(0, "John", "Doe");
        Customer customer1 = new Customer(1, "Jane", null);
        Customer customer2 = new Customer(2, null, "Doe");
        Customer customer3 = new Customer(3, null, null);

        assertEquals(customer0.customerId, 0);
        assertEquals(customer0.firstName, "John");
        assertEquals(customer0.lastName, "Doe");

        assertEquals(customer1.customerId, 1);
        assertEquals(customer1.firstName, "Jane");
        assertNull(customer1.lastName);

        assertEquals(customer2.customerId, 2);
        assertNull(customer2.firstName);
        assertEquals(customer2.lastName, "Doe");

        assertEquals(customer3.customerId, 3);
        assertNull(customer3.firstName);
        assertNull(customer3.lastName);
    }

    @Test
    public void testCustomerTable() {
        Customer customer0 = CustomerTable.getInstance().createCustomer(null, null);
        Customer customer1 = CustomerTable.getInstance().createCustomer("John", null);
        Customer customer2 = CustomerTable.getInstance().createCustomer(null, "Doe");
        Customer customer3 = CustomerTable.getInstance().createCustomer("Jane", "Doe");

        assertNotEquals(customer0.customerId, customer1.customerId);
        assertNotEquals(customer0.customerId, customer2.customerId);
        assertNotEquals(customer0.customerId, customer3.customerId);
        assertNotEquals(customer1.customerId, customer2.customerId);
        assertNotEquals(customer1.customerId, customer3.customerId);
        assertNotEquals(customer2.customerId, customer3.customerId);

        assertNull(customer0.firstName);
        assertNull(customer0.lastName);
        assertEquals(customer1.firstName, "John");
        assertNull(customer1.lastName);
        assertNull(customer2.firstName);
        assertEquals(customer2.lastName, "Doe");
        assertEquals(customer3.firstName, "Jane");
        assertEquals(customer3.lastName, "Doe");
    }

    @Test
    public void testOrderItem() {
        OrderItem orderItem0 = new OrderItem(0, "A", 100, 100);
        OrderItem orderItem1 = new OrderItem(1, "B", 50, 0);

        assertEquals(orderItem0.id, 0);
        assertEquals(orderItem0.itemId, "A");
        assertEquals(orderItem0.quantity, 100);
        assertEquals(orderItem0.discount, 100);

        assertEquals(orderItem1.id, 1);
        assertEquals(orderItem1.itemId, "B");
        assertEquals(orderItem1.quantity, 50);
        assertEquals(orderItem1.discount, 0);
    }

    @Test
    public void testOrder() {
        try {
            PurchasableItem purchasable0 = PurchasableItems.getInstance().createPurchasable("XYZ-0", "Item A", 10, 11);
            PurchasableItem purchasable1 = PurchasableItems.getInstance().createPurchasable("XYZ-1", "Item B", 11, 10);
            PurchasableItem purchasable2 = PurchasableItems.getInstance().createPurchasable("XYZ-2", "Item C", 100, 10);

            Order order = new Order("0001", 0);

            int lineItem0 = order.addOrderItem(purchasable0.itemId, 9, 0);
            assertEquals(90, order.getTotal());
            OrderItem lineItem = order.getOrderItem(lineItem0);

            if (lineItem != null) {
                lineItem.discount = 20;

                //Order total should be 90
                assertEquals(90, order.getTotal());   // 4
                order.updateOrderItem(lineItem0, 9, 20);
                assertEquals(72, order.getTotal());
            }

            int lineItem1 = order.addOrderItem(purchasable1.itemId, 10, 13);
            assertEquals(168, order.getTotal());

            int lineItem2 = order.addOrderItem(purchasable2.itemId, 1, 105);
            assertEquals(268, order.getTotal()); // 3

            int lineItem3 = order.addOrderItem(purchasable0.itemId, 2, 0);
            PurchasableItems.getInstance().updatePurchasable(purchasable0.sku, purchasable0.price, 10);
            assertEquals(278, order.getTotal());  // 6

        } catch (Exception e) {
            e.printStackTrace();
            assert false;

        }
    }

    @Test
    public void testOrderTable() {
        Order order0 = OrderTable.getInstance().createOrder(0);
        Order order1 = OrderTable.getInstance().createOrder(0);
        Order order2 = OrderTable.getInstance().createOrder(0);
        Order order3 = OrderTable.getInstance().createOrder(1);

        assertNotEquals(order0.orderId, order1.orderId);
        assertNotEquals(order0.orderId, order2.orderId);
        assertNotEquals(order0.orderId, order3.orderId);
        assertNotEquals(order1.orderId, order2.orderId);
        assertNotEquals(order1.orderId, order3.orderId);
        assertNotEquals(order2.orderId, order3.orderId);

        ArrayList<Order> orders0 = OrderTable.getInstance().getOrdersByCustomer(0);
        ArrayList<Order> orders1 = OrderTable.getInstance().getOrdersByCustomer(1);
        ArrayList<Order> orders2 = OrderTable.getInstance().getOrdersByCustomer(2);

        assertEquals(3, orders0.size());
        assertEquals(1, orders1.size());
        assertEquals(0, orders2.size());
    }

    @Test
    public void testPurchasableItem() {
        PurchasableItem purchasable0 = new PurchasableItem("A", "XYZ-0", "Item A", 100, 1);
        PurchasableItem purchasable1 = new PurchasableItem("B", "XYZ-1", "Item B", -100, 1);
        PurchasableItem purchasable2 = new PurchasableItem("C", "XYZ-2", "Item C", 100, -1);

        assertEquals("A", purchasable0.itemId);
        assertEquals("XYZ-0", purchasable0.sku);
        assertEquals("Item A", purchasable0.description);
        assertEquals(100, purchasable0.price);
        assertEquals(1, purchasable0.stock);

        assertEquals("B", purchasable1.itemId);
        assertEquals("XYZ-1", purchasable1.sku);
        assertEquals("Item B", purchasable1.description);
        assertTrue(purchasable1.price >= 0);  // 1
        assertTrue(purchasable1.stock >= 0);

        assertEquals("C", purchasable2.itemId);
        assertEquals("XYZ-2", purchasable2.sku);
        assertEquals("Item C", purchasable2.description);
        assertEquals(0, purchasable2.stock);  // 2
    }

    @Test
    public void testPurchasableItems() {
        try {
            PurchasableItem purchasable0 = PurchasableItems.getInstance().createPurchasable("XYZ-0", "Item A", 100, 10);

            assertEquals("XYZ-0", purchasable0.sku);
            assertEquals("Item A", purchasable0.description);
            assertEquals(100, purchasable0.price);
            assertEquals(10, purchasable0.stock);

            purchasable0.price = 10;
            PurchasableItem purchasable = PurchasableItems.getInstance().getPurchasableSku(purchasable0.sku);
            assertEquals(100, purchasable.price); // 5

            purchasable = PurchasableItems.getInstance().updatePurchasable("XYZ-0", 10, 10);
            assertEquals(10, purchasable.price);

            PurchasableItem existing0 = PurchasableItems.getInstance().getPurchasable(purchasable0.itemId);
            PurchasableItem existing1 = PurchasableItems.getInstance().getPurchasableSku(purchasable0.sku);

            assertNotNull(existing0);
            assertNotNull(existing1);

            assert (purchasable.itemId == existing0.itemId);
            assert (purchasable.sku == existing0.sku);
            assert (purchasable.description == existing0.description);
            assert (purchasable.price == existing0.price);
            assert (purchasable.stock == existing0.stock);

            assert (purchasable.itemId == existing1.itemId);
            assert (purchasable.sku == existing1.sku);
            assert (purchasable.description == existing1.description);
            assert (purchasable.price == existing1.price);
            assert (purchasable.stock == existing1.stock);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

        try {
            PurchasableItem purchasableItem = PurchasableItems.getInstance().createPurchasable("XYZ-0", "Item A", 100, 10);
            assert false;
        } catch (Exception e) {
        }
    }

}