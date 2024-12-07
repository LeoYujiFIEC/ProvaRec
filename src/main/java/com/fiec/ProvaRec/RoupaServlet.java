package com.fiec.ProvaRec;

import com.fiec.ProvaRec.models.Roupa;
import com.fiec.ProvaRec.models.RoupaRepositorioImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/roupas/*")
public class RoupaServlet extends HttpServlet {

    private final RoupaRepositorioImpl roupaRepositorio;

    public RoupaServlet() {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProvaRec").createEntityManager();
        this.roupaRepositorio = new RoupaRepositorioImpl(entityManager);
    }

    // Create /produtos
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String modelo = request.getParameter("modelo");
        String preco = request.getParameter("preco");
        String cor = request.getParameter("cor");
        String tamanho = request.getParameter("tamanho");

        Roupa roupa = Roupa.builder()
                .modelo(modelo)
                .preco(Double.parseDouble(preco))
                .cor(cor)
                .tamanho(tamanho)
                .build();

        Roupa salvo = roupaRepositorio.criar(roupa);

        response.setContentType("application/json");
        response.getWriter().println("Roupa salva com sucesso: " + "ID " + salvo.getId());
    }

    // Read /produtos
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = getId(request);

        response.setContentType("application/json");

        if (id == null) {
            response.getWriter().println(roupaRepositorio.ler());
        } else {
            Roupa roupa = roupaRepositorio.lerPorId(Long.parseLong(id));
            if (roupa != null) {
                response.getWriter().println(roupa);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("Roupa não encontrado");
            }
        }
    }

    // Update /produtos/:id
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = getId(request);
        String modelo = request.getParameter("modelo");
        String preco = request.getParameter("preco");
        String cor = request.getParameter("cor");
        String tamanho = request.getParameter("tamanho");

        Roupa roupaAtualizado = Roupa.builder()
                .modelo(modelo)
                .preco(Double.parseDouble(preco))
                .cor(cor)
                .tamanho(tamanho)
                .build();

        roupaRepositorio.atualiza(roupaAtualizado, Long.parseLong(id));
        response.setContentType("application/json");
        response.getWriter().println("Roupa atualizado com sucesso: " + "ID " + roupaAtualizado.getId());
    }

    // Delete /produtos/:id
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = getId(request);
        roupaRepositorio.deleta(Long.parseLong(id));
        response.setContentType("application/json");
        response.getWriter().println("Roupa deletado com sucesso: " + "ID " + id);
    }

    private static String getId(HttpServletRequest req) {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            return null;
        }
        String[] paths = path.split("/");
        return paths[paths.length - 1];
    }
}
