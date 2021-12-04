package com.revature.crypto;

import com.revature.CryptoORM_P1.annotations.Column;
import com.revature.CryptoORM_P1.annotations.Table;
import com.revature.CryptoORM_P1.annotations.Value;
import com.revature.CryptoORM_P1.mapper.SQLMapper;

import java.sql.SQLException;
import java.util.Properties;

@Table(tableName = "Test_Annotation")
public class TestDependencyDriver {

    static SQLMapper sqlMapper = SQLMapper.getInstance();

    @Column(columnName = "Test_Column_Annotation")
    String testString;

    @Value(correspondingColumn = "Test_Column_Annotation")
    public String getTestString() {
        return this.testString;
    }

    TestDependencyDriver(String testString) {
        this.testString = testString;
    }

    public static void main(String[] args) throws SQLException {
        TestDependencyDriver test = new TestDependencyDriver("This is a test string!");

        System.out.println(sqlMapper.insert(test));


    }

}
