/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.ear.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@PropertySource("classpath:eclipselink.properties")
public class PersistenceConfig extends JpaBaseConfiguration{
    
    private final Environment environment;
    
    protected PersistenceConfig(DataSource dataSource,
                                JpaProperties properties,
                                ObjectProvider<JtaTransactionManager> jtaTransactionManager,
                                Environment environment) {
        super(dataSource, properties, jtaTransactionManager);
        this.environment = environment;
    }
    
    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        final Map<String, Object> props = new HashMap<>();
        props.put("eclipselink.weaving", "static");
        props.put("eclipselink.ddl-generation", environment.getRequiredProperty("eclipselink.ddl-generation"));
        return props;
    }
}
