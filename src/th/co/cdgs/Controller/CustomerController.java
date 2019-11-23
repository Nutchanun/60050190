package th.co.cdgs.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import th.co.cdgs.been.customerDto;

@Path("customer")
public class CustomerController {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCustomer(customerDto customer) {
		List<customerDto> list = new ArrayList<customerDto>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop", "root", "p@ssw0rd");
			pst = conn.prepareStatement("INSERT INTO customer (first_name ,last_name,address,tel,email)"+"VALUES (?,?,?,?,?)");
			int index = 1 ;
			
			pst.setString(index++, customer.getFirstName());
			pst.setString(index++, customer.getLastName());
			pst.setString(index++, customer.getAddress());
			pst.setString(index++, customer.getTel());
			pst.setString(index++, customer.getEmail());
			pst.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {

				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return Response.ok().entity(list).build();

	}
	
	@DELETE
	@Path("{id}")
	public Response deleteCustomer(@PathParam("id") Long customerID ) { 
	  ResultSet rs =null;
	  PreparedStatement pst =null;
	  Connection conn =null;
	  try {

		  	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop", "root", "p@ssw0rd");
		  	pst = conn. prepareStatement("DELETE FROM customer WHERE customer_Id = ?");
		  	int index = 1;
		  	pst.setLong(index++, customerID);
		  	pst.executeUpdate();
	  		} catch (SQLException e) {e.printStackTrace();}
	  finally {
		  		if(rs!=null) 
		  		{
		  			try {rs.close();
		  		}
		  		catch (SQLException e){ }
		  	  }
	    if(pst!=null) {
	     try {pst.close();}
	     catch (SQLException e){ }
	    }
	    if(conn!=null) {
	     try {conn.close();}
	     catch (SQLException e){ }
	    }
	   
	   }
	  return Response.ok().entity(true).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer() {
		List<customerDto> list = new ArrayList<customerDto>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop", "root", "p@ssw0rd");
			pst = conn.prepareStatement("SELECT customer_Id ," + "CONCAT(first_name,'',last_name)as full_name,"
					+ "address,tel,email FROM customer");
			rs = pst.executeQuery();
			customerDto customerDto = null;
			while (rs.next()) {
				customerDto = new customerDto();
				customerDto.setCustomerId(rs.getLong("customer_Id"));
				customerDto.setFullName(rs.getString("full_name"));
				customerDto.setAddress(rs.getString("address"));
				customerDto.setTel(rs.getString("tel"));
				customerDto.setEmail(rs.getString("email"));
				list.add(customerDto);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {

				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return Response.ok().entity(list).build();

	}
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCustomer(customerDto customer) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/workshop", "root", "p@ssw0rd");
				pst = conn.prepareStatement(
						"UPDATE customer  SET " + 
						"first_name  = ? ," + 
						"last_name = ? ," + 
						"address = ? , " + 
						"tel= ?  , " + 
						"email = ? " + 
						"WHERE customer_Id = ? ");
				int index = 1;
				pst.setString(index++, customer.getFirstName());
				pst.setString(index++, customer.getLastName());
				pst.setString(index++, customer.getAddress());
				pst.setString(index++, customer.getTel());
				pst.setString(index++, customer.getEmail());
				pst.setLong(index++, customer.getCustomerId());
				pst.executeUpdate();
			}
		catch (SQLException e) {
	        e.printStackTrace();
	       }
	       finally {
	        if(rs!=null) {
	         try {rs.close();}
	         catch (SQLException e){ }
	        }
	        if(pst!=null) {
	         try {pst.close();}
	         catch (SQLException e){ }
	        }
	        if(conn!=null) {
	         try {conn.close();}
	         catch (SQLException e){ }
	        }
	       
	       }
	      return Response.ok().entity(true).build();
	     }
	


}
