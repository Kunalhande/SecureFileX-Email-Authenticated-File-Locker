package com.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.db.MyConnection;
import com.model.Data;

public class datadao {

    public static List<Data> getAllFiles(String email) throws SQLException {

        Connection connection = MyConnection.getConnection();

        PreparedStatement ps = connection.prepareStatement(
                "select * from data where email = ?"
        );

        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        List<Data> files = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String path = rs.getString(3);

            files.add(new Data(id, name, path));
        }

        return files;
    }

    public static int hideFile(Data file) throws SQLException, IOException {

        Connection connection = MyConnection.getConnection();

        PreparedStatement ps = connection.prepareStatement(
                "insert into data(name, path, email, bin_data) values (?, ?, ?, ?)"
        );

        ps.setString(1, file.getFileName());
        ps.setString(2, file.getPath());
        ps.setString(3, file.getEmail());

        File f = new File(file.getPath());

        FileInputStream fis = new FileInputStream(f);
        ps.setBinaryStream(4, fis, (int) f.length());

        int ans = ps.executeUpdate();

        fis.close();
        f.delete();   // delete original file after storing

        return ans;
    }

    public static void unhide(int id) throws SQLException, IOException {

        Connection connection = MyConnection.getConnection();

        PreparedStatement ps = connection.prepareStatement(
                "select path, bin_data from data where id = ?"
        );

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            String path = rs.getString("path");

            Blob blob = rs.getBlob("bin_data");

            InputStream is = blob.getBinaryStream();

            FileOutputStream fos = new FileOutputStream(path);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            fos.close();
            is.close();

            PreparedStatement deletePs = connection.prepareStatement(
                    "delete from data where id = ?"
            );

            deletePs.setInt(1, id);
            deletePs.executeUpdate();

            System.out.println("Successfully Unhidden");
        }
    }
}
