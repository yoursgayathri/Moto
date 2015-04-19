package Controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.customerDAO;
import Models.Customer;
import java.text.DateFormat;

public class customerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/customer.jsp";
    private static String LIST_USER = "/listCustomer.jsp";
   
    private customerDAO dao;

    public customerController() {
        
        super();
        dao = new customerDAO();
        System.out.println("constcurto");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");
        System.out.println(request+"action is"+action);

        if (action.equalsIgnoreCase("delete")){
            String customerId = request.getParameter("customer_id");
            dao.deleteCustomer(customerId);
            forward = LIST_USER;
            request.setAttribute("customers", dao.getAllCustomers());    
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            String customerId = request.getParameter("customer_id");
            Customer customer = dao.getCustomerById(customerId);
            request.setAttribute("customer", customer);
        } else if (action.equalsIgnoreCase("listCustomer")){
             forward = LIST_USER;
            request.setAttribute("customers", dao.getAllCustomers());
          } 
         else {
            forward = INSERT_OR_EDIT;
            System.out.println("i am in insert ");
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = new Customer();
        String customer_id=request.getParameter("customer_id");
        String first_name=request.getParameter("first_name");
            String last_name=request.getParameter("last_name");
            String dob=request.getParameter("dob");
            System.out.println("dob is"+dob);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
            Date startDate=null;
            try {
                startDate = df.parse(dob);
                 System.out.println(new java.sql.Date(startDate.getTime())+"start date is "+startDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String gender=request.getParameter("gender");
            String address_line1=request.getParameter("address_line1");
            String address_line2=request.getParameter("address_line2");
            String city=request.getParameter("city");
            String state=request.getParameter("state");
            String zip=request.getParameter("zip");
            String home_phone=request.getParameter("home_phone");
            String cell_phone=request.getParameter("cell_phone");
            String email=request.getParameter("email");
            customer.setCustomer_id(customer_id);           
            customer.setFirst_name(first_name);
            customer.setLast_name(last_name);
            customer.setDob(new java.sql.Date(startDate.getTime()));
            customer.setGender(gender);
            customer.setAddress_line1(address_line1);
            customer.setAddress_line2(address_line2);
            customer.setCity(city);
            customer.setState(state);
            customer.setZip(Integer.parseInt(zip));
            customer.setCell_phone(cell_phone);
            customer.setHome_phone(home_phone);
            customer.setEmail(email);
                
        String customerid = request.getParameter("customer_id");
        System.out.println("heloooa"+customerid);
        if(customerid == null || customerid.isEmpty())
        {
            dao.addCustomer(customer);
        }
        else
        {
            System.out.println("I am in updating");
            customer.setCustomer_id(customerid);
            dao.updateCustomer(customer);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
        request.setAttribute("customers", dao.getAllCustomers());
        view.forward(request, response);
    }
}