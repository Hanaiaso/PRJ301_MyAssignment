package Employee.Controller;

import Employee.Entity.Employee;
import dal.DBContext;
import dal.EmployeeDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet to handle deletion of multiple employees.
 */
public class EmployeeDeleteAllController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EmployeeDBContext db = new EmployeeDBContext();
        ArrayList<Employee> emps = db.list();
        request.setAttribute("emps", emps);
        request.getRequestDispatcher("../view/employee/delete.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] employeeIds = request.getParameterValues("employeeIds");

        if (employeeIds != null && employeeIds.length > 0) {
            // Kết nối đến cơ sở dữ liệu
            
            PreparedStatement pstmt = null;

            try {
                //conn = DBContext.initializeDatabase();

                // Tạo câu SQL với số lượng placeholder phù hợp
                StringBuilder queryBuilder = new StringBuilder("DELETE FROM employees WHERE id IN (");
                for (int i = 0; i < employeeIds.length; i++) {
                    queryBuilder.append("?");
                    if (i < employeeIds.length - 1) {
                        queryBuilder.append(",");
                    }
                }
                queryBuilder.append(")");

                pstmt = Connection.prepareStatement(queryBuilder.toString());

                // Truyền các giá trị ID nhân viên vào các vị trí placeholder
                for (int i = 0; i < employeeIds.length; i++) {
                    pstmt.setInt(i + 1, Integer.parseInt(employeeIds[i]));
                }

                // Thực thi câu lệnh SQL
                int rowsAffected = pstmt.executeUpdate();

                // Thông báo thành công nếu có bản ghi được xóa
                if (rowsAffected > 0) {
                    request.setAttribute("message", rowsAffected + " employee(s) deleted successfully.");
                } else {
                    request.setAttribute("message", "No employees were deleted.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("message", "An error occurred while deleting employees.");
            } finally {
                // Đóng tài nguyên
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            request.setAttribute("message", "No employee IDs selected.");
        }

        // Lấy lại danh sách nhân viên sau khi xóa và chuyển tiếp đến trang list.jsp
        EmployeeDBContext db = new EmployeeDBContext();
        ArrayList<Employee> emps = db.list();
        request.setAttribute("emps", emps);
        request.getRequestDispatcher("../view/employee/list.jsp").forward(request, response);
    }
}
