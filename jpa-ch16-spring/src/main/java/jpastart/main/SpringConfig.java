package jpastart.main;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "jpastart.reserve")
public class SpringConfig {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        dataSource.setJdbcUrl(
                "jdbc:mysql://localhost/jpastart?characterEncoding=utf8");
        dataSource.setUser("jpauser");
        dataSource.setPassword("jpapass");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean emfFactory() {
        LocalContainerEntityManagerFactoryBean emfBean =
                new LocalContainerEntityManagerFactoryBean();
        emfBean.setDataSource(dataSource());
        emfBean.setPersistenceUnitName("jpastart");
        HibernateJpaVendorAdapter jva = new HibernateJpaVendorAdapter();
        jva.setDatabase(Database.MYSQL);
        jva.setShowSql(true);
        emfBean.setJpaVendorAdapter(jva);
        return emfBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txMgr = new JpaTransactionManager();
        txMgr.setEntityManagerFactory(emf);
        return txMgr;
    }

}
