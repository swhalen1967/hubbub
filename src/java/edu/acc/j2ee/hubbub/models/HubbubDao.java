package edu.acc.j2ee.hubbub.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HubbubDao {
    public static final Pattern USER_PATTERN = Pattern.compile("^\\w{6,12}$");
    public static final Pattern PASS_PATTERN = Pattern.compile("^[\\w\\.-]{8,16}$");
    public static final Pattern JOIN_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    private Connection CONN = null;

    public HubbubDao(String jdbcUrl) throws SQLException {
        CONN = DriverManager.getConnection(jdbcUrl);
    }
    
    public HubbubModel<HubbubUser> register(HubbubRegisterBean bean) throws SQLException {
        HubbubModel<HubbubUser> model = new HubbubModel<>();
        if (bean.getUser() == null || bean.getPass1() == null || bean.getPass2() == null)
            return model.addError("One or more fields are missing in the request");
        if (!bean.getPass1().equals(bean.getPass2()))
            model.addError("Passwords do not match");
        if (!USER_PATTERN.matcher(bean.getUser()).matches())
            model.addError("User name field is empty or invalid");
        if (!PASS_PATTERN.matcher(bean.getPass1()).matches())
            model.addError("First password field is invalid");
        if (!PASS_PATTERN.matcher(bean.getPass2()).matches())
            model.addError("Second password field is invalid");
        if (model.hasErrors())
            return model;

        final String SQL1 = "INSERT INTO users (username,password) VALUES (?,?)";
        final String SQL2 = "SELECT * FROM users WHERE userid = ?";
        PreparedStatement ps1, ps2 = null;
        ResultSet rs1, rs2 = null;
        int insertId;
        ps1 = CONN.prepareStatement(SQL1, Statement.RETURN_GENERATED_KEYS);
        ps1.setString(1, bean.getUser());
        ps1.setString(2, bean.getPass1());
        ps1.executeUpdate();
        rs1 = ps1.getGeneratedKeys();
        if (rs1.next()) {
            insertId = rs1.getInt(1);
            ps2 = CONN.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS);
            ps2.setInt(1, insertId);
            rs2 = ps2.executeQuery();
            if (rs2.next())
                model.setModel( new HubbubUser(rs2.getString(1), rs2.getString(2),
                    rs2.getDate(3).toString(), rs2.getInt(4)));
        }
        rs1.close();
        if (rs2 != null) rs2.close();
        ps1.close();
        if (ps2 != null) ps2.close();
        return model;
    }
    
    public HubbubModel<HubbubUser> authenticate(String userName, String password) throws SQLException {
        HubbubModel<HubbubUser> model = new HubbubModel<>();
        if (!USER_PATTERN.matcher(userName).matches() ||
                !PASS_PATTERN.matcher(password).matches())
            return model.addError("Access Denied");
        final String SQL = "SELECT * FROM users WHERE username = ? and password = ?";
        PreparedStatement ps;
        ResultSet rs;
        ps = CONN.prepareStatement(SQL);
        ps.setString(1, userName);
        ps.setString(2, password);
        rs = ps.executeQuery();
        if (rs.next())
            model.setModel(new HubbubUser(rs.getString(1), rs.getString(2),
                rs.getDate(3).toString(), rs.getInt(4)));
        ps.close();
        rs.close();
        return model;
    }
    
    public HubbubModel<HubbubUser> getUserByUserid(int userid) throws SQLException {
        HubbubModel<HubbubUser> model = new HubbubModel<>();
        final String SQL = "SELECT * FROM users WHERE userid = ?";
        PreparedStatement ps;
        ResultSet rs;
        ps = CONN.prepareStatement(SQL);
        ps.setInt(1, userid);
        rs = ps.executeQuery();
        rs.next();
        model.setModel(new HubbubUser(rs.getString(1), rs.getString(2),
            rs.getDate(3).toString(), rs.getInt(4)));
        rs.close();
        ps.close();
        return model;
    }
    
    public HubbubModel<HubbubUser> getUserByUserName(String userName) throws SQLException {
        HubbubModel<HubbubUser> model = new HubbubModel<>();
        final String SQL = "SELECT * FROM users WHERE username = ?";
        PreparedStatement ps;
        ResultSet rs;
        ps = CONN.prepareStatement(SQL);
        ps.setString(1, userName);
        rs = ps.executeQuery();
        if (rs.next())
            model.setModel(new HubbubUser(rs.getString(1), rs.getString(2),
                rs.getDate(3).toString(), rs.getInt(4)));
        rs.close();
        ps.close();
        return model;        
    }
    
    public HubbubModel<HubbubPost> addPost(String content, HubbubUser user) throws SQLException {        
        HubbubModel<HubbubPost> model = new HubbubModel<>();
        content = content.trim();
        if (content == null || content.length() < 1)
            return model.addError("Attempted post had no content");
        if (content.length() > 200)
            return model.addError("Posts are limited to 200 characters");
        String now = LocalDateTime.now().toString().replace("T", " ");
        final String SQL =
            "INSERT INTO posts (content,postdate,userid) VALUES (?,'" + now + "',?)";
        PreparedStatement ps;
        ResultSet rs;
        ps = CONN.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, content);
        ps.setInt(2, user.getUserid());
        ps.executeUpdate();
        rs = ps.getGeneratedKeys();
        rs.next();
        int insertId = rs.getInt(1);
        model.setModel(new HubbubPost(content, now, user, insertId));
        rs.close();
        ps.close();
        return model;
    }
    
    public HubbubModel<List<HubbubPost>> getPostsByPostDateDescending(int postsPerPage)
    throws SQLException {
        HubbubModel<List<HubbubPost>> model = new HubbubModel<>();
        List<HubbubPost> posts = new ArrayList<>();
        final String SQL = "SELECT * FROM posts ORDER BY postdate DESC FETCH FIRST ? ROWS ONLY";
        PreparedStatement ps;
        ResultSet rs;
        ps = CONN.prepareStatement(SQL);
        ps.setInt(1, postsPerPage);
        rs = ps.executeQuery();
        while (rs.next()) {
            HubbubUser user = this.getUserByUserid(rs.getInt(3)).getModel();
            posts.add(new HubbubPost(rs.getString(1), rs.getTimestamp(2).toString(),
                user, rs.getInt(4)));
        }
        rs.close();
        ps.close();
        model.setModel(posts);
        return model;
    }
    
    public HubbubModel<List<HubbubPost>> getSomePosts(int count) throws SQLException {
        HubbubModel<List<HubbubPost>> model = new HubbubModel<>();
        List<HubbubPost> posts = new ArrayList<>();
        final String SQL = "SELECT * FROM posts ORDER BY postdate DESC FETCH FIRST ? ROWS ONLY";
        PreparedStatement ps;
        ResultSet rs;
        ps = CONN.prepareStatement(SQL);
        ps.setInt(1, count);
        rs = ps.executeQuery();
        while (rs.next()) {
            HubbubUser user = this.getUserByUserid(rs.getInt(3)).getModel();
            posts.add(new HubbubPost(rs.getString(1), rs.getTimestamp(2).toString(),
                user, rs.getInt(4)));
        }
        rs.close();
        ps.close();
        model.setModel(posts);
        return model;       
    }
    
    public void close() {
        try {CONN.close();} catch (Exception sqle){}
    }
}
