package uk.ac.ebi.chebi.ontology.web.server;

import uk.ac.ebi.chebi.ontology.core.util.DatabaseUtil;
import uk.ac.ebi.chebi.utilities.structure.transformer.StructureTransformer;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.*;

public class ChEBIImageServlet extends HttpServlet{
    Connection connection;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        try {
            this.connection= DatabaseUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, java.io.IOException {
        try {
            PreparedStatement statement= connection.prepareStatement("SELECT vpc.PARENT_ID,\n" +
                        "        CHEBI.STRUCTURES.STRUCTURE \n" +
                        "  FROM CHEBI.VW_PARENTS_AND_CHILDREN vpc,\n" +
                        "          CHEBI.STRUCTURES,\n" +
                        "          CHEBI.DEFAULT_STRUCTURES\n" +
                        "  WHERE vpc.CHILD_ID=CHEBI.STRUCTURES.COMPOUND_ID \n" +
                        "          AND CHEBI.DEFAULT_STRUCTURES.STRUCTURE_ID = CHEBI.STRUCTURES.ID\n" +
                        "          AND CHEBI.STRUCTURES.TYPE='mol' AND vpc.PARENT_ID=?");
            statement.setInt(1, Integer.parseInt(req.getParameter("id")));
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                String molfile=resultSet.getString(2);
                byte[] image= StructureTransformer.getImageFromMol(molfile);
                resp.setHeader("Expires", "0");
                resp.setHeader("Pragma", "No-cache");
                resp.addHeader("Cache-Control", "no-store, no-cache, max-age=0, must-revalidate");

                resp.setContentType("image/png");
                resp.getOutputStream().write(image, 0, image.length);
                resp.getOutputStream().close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
