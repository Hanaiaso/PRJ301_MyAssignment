package Product.Controller;

import Plan.Entity.Product;
import com.google.gson.Gson;
import dal.ProductDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author LEGION
 */
public class ProductUpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdParam = request.getParameter("pid");

        if (productIdParam != null && !productIdParam.isEmpty()) {
            int productId = Integer.parseInt(productIdParam);
            ProductDBContext pdb = new ProductDBContext();
            Product product = pdb.get(productId);
            if (product != null) {
                // Trả về thông tin sản phẩm dưới dạng JSON
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                Gson gson = new Gson();
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(product));
                out.flush();
            }
        } else {
            // Nếu không có sản phẩm cụ thể, trả về danh sách sản phẩm
            ProductDBContext pdb = new ProductDBContext();
            ArrayList<Product> p = pdb.list();
            request.setAttribute("plist", p);
            request.getRequestDispatcher("/view/product/product_delete.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Đọc JSON từ yêu cầu
        StringBuilder json = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        // Chuyển đổi từ JSON sang đối tượng Product
        Gson gson = new Gson();
        Product product = gson.fromJson(json.toString(), Product.class);

        // Cập nhật sản phẩm trong cơ sở dữ liệu
        ProductDBContext pdb = new ProductDBContext();
        pdb.update(product);

        // Trả về phản hồi thành công
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"status\": \"success\"}");
        out.flush();
        
    }
}
