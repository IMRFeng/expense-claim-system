package com.leyoho.ecs.security.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.leyoho.ecs.util.PasswordMgr;

/**
 * Initialize spring security tables to database.
 * <p>For convenient test, I used hsqldb as the default database here.</p>
 * 
 * @author Victor Feng
 */
public class DmsSecurityDSGenerator {
	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionManager;
	private static final PasswordMgr pwdService = new PasswordMgr();
	
	public DmsSecurityDSGenerator() {
	}
	
	public void init() throws Exception {
		Assert.notNull(jdbcTemplate, "dataSource required");
		Assert.notNull(transactionManager, "platformTransactionManager required");

		pwdService.setAlgorithm("sha-256");
		
		// Drop exists tables
		jdbcTemplate.execute("drop table if exists user");
		jdbcTemplate.execute("drop table if exists role");
		jdbcTemplate.execute("drop table if exists resources");
		jdbcTemplate.execute("drop table if exists user_role");
		jdbcTemplate.execute("drop table if exists role_resources");
		
		// Create tables
		jdbcTemplate.execute("create table user(id integer not null primary key, username varchar(50) not null, password varchar(100) not null, enable boolean not null);");
		jdbcTemplate.execute("create table role(id integer not null primary key, rolename varchar(50) not null);");
		jdbcTemplate.execute("create table resources(id integer not null primary key, url varchar(500) not null, memo varchar(50));");
		jdbcTemplate.execute("create table user_role(user_id integer not null, role_id integer not null, CONSTRAINT PK_UseRole PRIMARY KEY (user_id,role_id));");
		jdbcTemplate.execute("create table role_resources(role_id integer not null, resource_id integer not null, constraint pk_roleresource primary key (role_id,resource_id));");
		
		// Insert four users into user table
		jdbcTemplate.execute("insert into user values(1, 'administrator', '" + pwdService.digestMessage("administrator".getBytes()) + "', true);");
		jdbcTemplate.execute("insert into user values(2, 'victor', '" + pwdService.digestMessage("victor".getBytes()) + "', true);");
		jdbcTemplate.execute("insert into user values(3, 'peter', '" + pwdService.digestMessage("peter".getBytes()) + "', true);");
		jdbcTemplate.execute("insert into user values(4, 'john', '" + pwdService.digestMessage("john".getBytes()) + "', false);");
		
		// Add roles into role table.
		jdbcTemplate.execute("insert into role values(0, 'ROLE_SUPERMAN');");
		jdbcTemplate.execute("insert into role values(1, 'ROLE_ADMIN');");
		jdbcTemplate.execute("insert into role values(2, 'ROLE_USER');");
		
		// Insert resources into resource table
		jdbcTemplate.execute("insert into resources values(1, '/dashboard', 'Dashboard page');");
		jdbcTemplate.execute("insert into resources values(2, '/index', 'Index page');");
		jdbcTemplate.execute("insert into resources values(3, '/viewPerson', 'View Person page');");
		jdbcTemplate.execute("insert into resources values(4, '/aboutUs', 'About Us page');");
		jdbcTemplate.execute("insert into resources values(5, '/thread/all', 'Thread JSON page');");
		
		// Assign roles to corresponding users.
		jdbcTemplate.execute("insert into user_role values(1, 0);");
		jdbcTemplate.execute("insert into user_role values(2, 1);");
		jdbcTemplate.execute("insert into user_role values(3, 2);");
		jdbcTemplate.execute("insert into user_role values(4, 2);");

		// Assign resources to corresponding roles
		jdbcTemplate.execute("insert into role_resources values(1, 1);");
		jdbcTemplate.execute("insert into role_resources values(2, 1);");
		jdbcTemplate.execute("insert into role_resources values(1, 2);");
		jdbcTemplate.execute("insert into role_resources values(2, 2);");
		jdbcTemplate.execute("insert into role_resources values(1, 3);");
//		jdbcTemplate.execute("insert into role_resources values(1, 4);");
		jdbcTemplate.execute("insert into role_resources values(1, 5);");
		jdbcTemplate.execute("insert into role_resources values(2, 3);");
		
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setTransactionManager(PlatformTransactionManager platformTransactionManager) {
		this.transactionManager = new TransactionTemplate(platformTransactionManager);
	}
}
