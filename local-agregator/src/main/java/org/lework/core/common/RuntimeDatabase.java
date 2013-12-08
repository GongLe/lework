package org.lework.core.common;


/**
 *  APP runtime JDBC database.
 * 
 * <p>
 * If APP runs on local environment, APP will read database configurations from file "local.properties".
 * </p>
 *  
 * @see Configs#getRuntimeDatabase() 
 */
public enum RuntimeDatabase {

    /**
     * Oracle.
     */
    ORACLE,
    /**
     * MySQL.
     */
    MYSQL,
    /**
     * H2.
     */
    H2,
    /**
     * SYBASE.
     */
    SYBASE,
    /**
     * MSSQL.
     */
    MSSQL,
    /**
     * DB2.
     */
    DB2
}
