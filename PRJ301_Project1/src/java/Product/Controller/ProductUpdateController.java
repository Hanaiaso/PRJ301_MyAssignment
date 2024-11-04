package Product.Controller;

import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
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
public class ProductUpdateController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        String productIdParam = req.getParameter("pid");

        if (productIdParam != null && !productIdParam.isEmpty()) {
            int productId = Integer.parseInt(productIdParam);
            ProductDBContext pdb = new ProductDBContext();
            Product product = pdb.get(productId);
            if (product != null) {
                // Trả về thông tin sản phẩm dưới dạng JSON
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                Gson gson = new Gson();
                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(product));
                out.flush();
            }
        } else {
            // Nếu không có sản phẩm cụ thể, trả về danh sách sản phẩm
            ProductDBContext pdb = new ProductDBContext();
            ArrayList<Product> p = pdb.list();
            req.setAttribute("plist", p);
            req.getRequestDispatcher("/view/product/product_update.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        // Đọc JSON từ yêu cầu
        StringBuilder json = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
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
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"status\": \"success\"}");
        out.flush();

    }

}
