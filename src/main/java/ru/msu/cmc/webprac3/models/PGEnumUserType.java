//package ru.msu.cmc.webprac3.models;
//
//
//import java.io.Serializable;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Types;
//import java.util.Objects;
//import java.util.Properties;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import org.hibernate.HibernateException;
//import org.hibernate.engine.spi.SharedSessionContractImplementor;
//import org.hibernate.usertype.EnhancedUserType;
//import org.hibernate.usertype.ParameterizedType;
//import org.hibernate.usertype.UserType;
//import org.postgresql.util.PGobject;
//
//
//public class MyEnumConverter implements UserType {
//
//    private static final int[] SQL_TYPES = new int[]{Types.OTHER};
//
//    public Object nullSafeGet(ResultSet arg0, String[] arg1, Object arg2) throws HibernateException, SQLException {
//
//        Object pgObject = arg0.getObject(status); // X is the column containing the enum
//
//        try {
//            Method valueMethod = pgObject.getClass().getMethod("getValue");
//            String value = (String)valueMethod.invoke(pgObject);
//            return Mood.valueOf(value);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public int[] sqlTypes() {
//        return SQL_TYPES;
//    }
//
//    // Rest of methods omitted
//
//}