package com.yjm.doctor.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yjm.doctor.model.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, Integer> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, int.class, "id", true, "ID");
        public final static Property Amount = new Property(1, int.class, "amount", false, "AMOUNT");
        public final static Property Email = new Property(2, String.class, "email", false, "EMAIL");
        public final static Property Groupid = new Property(3, int.class, "groupid", false, "GROUPID");
        public final static Property HxPassword = new Property(4, String.class, "hxPassword", false, "HX_PASSWORD");
        public final static Property HxStatus = new Property(5, boolean.class, "hxStatus", false, "HX_STATUS");
        public final static Property IsAdmin = new Property(6, int.class, "isAdmin", false, "IS_ADMIN");
        public final static Property LastLoginIp = new Property(7, int.class, "lastLoginIp", false, "LAST_LOGIN_IP");
        public final static Property LastLoginTime = new Property(8, int.class, "lastLoginTime", false, "LAST_LOGIN_TIME");
        public final static Property Login = new Property(9, int.class, "login", false, "LOGIN");
        public final static Property Mobile = new Property(10, String.class, "mobile", false, "MOBILE");
        public final static Property Modelid = new Property(11, int.class, "modelid", false, "MODELID");
        public final static Property Password = new Property(12, String.class, "password", false, "PASSWORD");
        public final static Property Pic = new Property(13, String.class, "pic", false, "PIC");
        public final static Property RegIp = new Property(14, int.class, "regIp", false, "REG_IP");
        public final static Property RegTime = new Property(15, long.class, "regTime", false, "REG_TIME");
        public final static Property Score = new Property(16, int.class, "score", false, "SCORE");
        public final static Property Status = new Property(17, int.class, "status", false, "STATUS");
        public final static Property TokenId = new Property(18, String.class, "tokenId", false, "TOKEN_ID");
        public final static Property UpdateTime = new Property(19, int.class, "updateTime", false, "UPDATE_TIME");
        public final static Property Username = new Property(20, String.class, "username", false, "USERNAME");
    }


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"ID\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"AMOUNT\" INTEGER NOT NULL ," + // 1: amount
                "\"EMAIL\" TEXT," + // 2: email
                "\"GROUPID\" INTEGER NOT NULL ," + // 3: groupid
                "\"HX_PASSWORD\" TEXT," + // 4: hxPassword
                "\"HX_STATUS\" INTEGER NOT NULL ," + // 5: hxStatus
                "\"IS_ADMIN\" INTEGER NOT NULL ," + // 6: isAdmin
                "\"LAST_LOGIN_IP\" INTEGER NOT NULL ," + // 7: lastLoginIp
                "\"LAST_LOGIN_TIME\" INTEGER NOT NULL ," + // 8: lastLoginTime
                "\"LOGIN\" INTEGER NOT NULL ," + // 9: login
                "\"MOBILE\" TEXT," + // 10: mobile
                "\"MODELID\" INTEGER NOT NULL ," + // 11: modelid
                "\"PASSWORD\" TEXT," + // 12: password
                "\"PIC\" TEXT," + // 13: pic
                "\"REG_IP\" INTEGER NOT NULL ," + // 14: regIp
                "\"REG_TIME\" INTEGER NOT NULL ," + // 15: regTime
                "\"SCORE\" INTEGER NOT NULL ," + // 16: score
                "\"STATUS\" INTEGER NOT NULL ," + // 17: status
                "\"TOKEN_ID\" TEXT," + // 18: tokenId
                "\"UPDATE_TIME\" INTEGER NOT NULL ," + // 19: updateTime
                "\"USERNAME\" TEXT);"); // 20: username
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, User entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getAmount());
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(3, email);
        }
        stmt.bindLong(4, entity.getGroupid());
 
        String hxPassword = entity.getHxPassword();
        if (hxPassword != null) {
            stmt.bindString(5, hxPassword);
        }
        stmt.bindLong(6, entity.getHxStatus() ? 1L: 0L);
        stmt.bindLong(7, entity.getIsAdmin());
        stmt.bindLong(8, entity.getLastLoginIp());
        stmt.bindLong(9, entity.getLastLoginTime());
        stmt.bindLong(10, entity.getLogin());
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(11, mobile);
        }
        stmt.bindLong(12, entity.getModelid());
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(13, password);
        }
 
        String pic = entity.getPic();
        if (pic != null) {
            stmt.bindString(14, pic);
        }
        stmt.bindLong(15, entity.getRegIp());
        stmt.bindLong(16, entity.getRegTime());
        stmt.bindLong(17, entity.getScore());
        stmt.bindLong(18, entity.getStatus());
 
        String tokenId = entity.getTokenId();
        if (tokenId != null) {
            stmt.bindString(19, tokenId);
        }
        stmt.bindLong(20, entity.getUpdateTime());
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(21, username);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getAmount());
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(3, email);
        }
        stmt.bindLong(4, entity.getGroupid());
 
        String hxPassword = entity.getHxPassword();
        if (hxPassword != null) {
            stmt.bindString(5, hxPassword);
        }
        stmt.bindLong(6, entity.getHxStatus() ? 1L: 0L);
        stmt.bindLong(7, entity.getIsAdmin());
        stmt.bindLong(8, entity.getLastLoginIp());
        stmt.bindLong(9, entity.getLastLoginTime());
        stmt.bindLong(10, entity.getLogin());
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(11, mobile);
        }
        stmt.bindLong(12, entity.getModelid());
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(13, password);
        }
 
        String pic = entity.getPic();
        if (pic != null) {
            stmt.bindString(14, pic);
        }
        stmt.bindLong(15, entity.getRegIp());
        stmt.bindLong(16, entity.getRegTime());
        stmt.bindLong(17, entity.getScore());
        stmt.bindLong(18, entity.getStatus());
 
        String tokenId = entity.getTokenId();
        if (tokenId != null) {
            stmt.bindString(19, tokenId);
        }
        stmt.bindLong(20, entity.getUpdateTime());
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(21, username);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.getInt(offset + 0);
    }    

    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.getInt(offset + 0), // id
            cursor.getInt(offset + 1), // amount
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // email
            cursor.getInt(offset + 3), // groupid
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // hxPassword
            cursor.getShort(offset + 5) != 0, // hxStatus
            cursor.getInt(offset + 6), // isAdmin
            cursor.getInt(offset + 7), // lastLoginIp
            cursor.getInt(offset + 8), // lastLoginTime
            cursor.getInt(offset + 9), // login
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // mobile
            cursor.getInt(offset + 11), // modelid
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // password
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // pic
            cursor.getInt(offset + 14), // regIp
            cursor.getLong(offset + 15), // regTime
            cursor.getInt(offset + 16), // score
            cursor.getInt(offset + 17), // status
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // tokenId
            cursor.getInt(offset + 19), // updateTime
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20) // username
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.getInt(offset + 0));
        entity.setAmount(cursor.getInt(offset + 1));
        entity.setEmail(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGroupid(cursor.getInt(offset + 3));
        entity.setHxPassword(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setHxStatus(cursor.getShort(offset + 5) != 0);
        entity.setIsAdmin(cursor.getInt(offset + 6));
        entity.setLastLoginIp(cursor.getInt(offset + 7));
        entity.setLastLoginTime(cursor.getInt(offset + 8));
        entity.setLogin(cursor.getInt(offset + 9));
        entity.setMobile(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setModelid(cursor.getInt(offset + 11));
        entity.setPassword(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setPic(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setRegIp(cursor.getInt(offset + 14));
        entity.setRegTime(cursor.getLong(offset + 15));
        entity.setScore(cursor.getInt(offset + 16));
        entity.setStatus(cursor.getInt(offset + 17));
        entity.setTokenId(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setUpdateTime(cursor.getInt(offset + 19));
        entity.setUsername(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
     }
    
    @Override
    protected final Integer updateKeyAfterInsert(User entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public Integer getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(User entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
