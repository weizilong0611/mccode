package com.jeesite.modules.config;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.shiro.cas.CasOutHandler;
import com.jeesite.common.shiro.config.FilterChainDefinitionMap;
import com.jeesite.common.shiro.filter.CasAuthenticationFilter;
import com.jeesite.common.shiro.filter.FormAuthenticationFilter;
import com.jeesite.common.shiro.filter.InnerFilter;
import com.jeesite.common.shiro.filter.LogoutFilter;
import com.jeesite.common.shiro.filter.PermissionsAuthorizationFilter;
import com.jeesite.common.shiro.filter.RolesAuthorizationFilter;
import com.jeesite.common.shiro.filter.UserFilter;
import com.jeesite.common.shiro.realm.AuthorizingRealm;
import com.jeesite.common.shiro.realm.BaseAuthorizingRealm;
import com.jeesite.common.shiro.realm.CasAuthorizingRealm;
import com.jeesite.common.shiro.session.SessionDAO;
import com.jeesite.common.shiro.session.SessionManager;
import com.jeesite.common.shiro.web.ShiroFilterFactoryBean;
import com.jeesite.common.shiro.web.WebSecurityManager;
import com.jeesite.modules.face.shiro.real.FaceRealmAuthenticator;

import java.util.Collection;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm; 
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

@Configuration
public class ShiroConfig {
  @Bean
  @Order(3000)
  @ConditionalOnMissingBean(name = {"shiroFilterProxy"})
  public FilterRegistrationBean<Filter> shiroFilterProxy(ShiroFilterFactoryBean shiroFilter) throws Exception {
    FilterRegistrationBean<Filter> bean = new FilterRegistrationBean();
    bean.setFilter((Filter)shiroFilter.getInstance());
    bean.addUrlPatterns(new String[] { "/*" });
    return bean;
  }
  
  private InnerFilter shiroInnerFilter() {
    return new InnerFilter();
  }
  
  private CasAuthenticationFilter shiroCasFilter(CasAuthorizingRealm casAuthorizingRealm) {
    CasAuthenticationFilter bean = new CasAuthenticationFilter();
    bean.setAuthorizingRealm(casAuthorizingRealm);
    return bean;
  }
  
  private FormAuthenticationFilter shiroAuthcFilter(AuthorizingRealm authorizingRealm) {
    FormAuthenticationFilter bean = new FormAuthenticationFilter();
    bean.setAuthorizingRealm((BaseAuthorizingRealm)authorizingRealm);
    return bean;
  }
  
  
  
  private LogoutFilter shiroLogoutFilter(AuthorizingRealm authorizingRealm) {
    LogoutFilter bean = new LogoutFilter();
    bean.setAuthorizingRealm((BaseAuthorizingRealm)authorizingRealm);
    return bean;
  }
  
  private PermissionsAuthorizationFilter shiroPermsFilter() {
    return new PermissionsAuthorizationFilter();
  }
  
  private RolesAuthorizationFilter shiroRolesFilter() {
    return new RolesAuthorizationFilter();
  }
  
  private UserFilter shiroUserFilter() {
    return new UserFilter();
  }
  
  @Bean
  public ShiroFilterFactoryBean shiroFilter(WebSecurityManager securityManager, AuthorizingRealm authorizingRealm, CasAuthorizingRealm casAuthorizingRealm) {
    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
    bean.setSecurityManager((SecurityManager)securityManager);
    bean.setLoginUrl(Global.getProperty("shiro.loginUrl"));
    bean.setSuccessUrl(Global.getProperty("adminPath") + "/index");
    Map<String, Filter> filters = bean.getFilters();
    filters.put("inner", shiroInnerFilter());
    filters.put("cas", shiroCasFilter(casAuthorizingRealm));
    filters.put("authc", shiroAuthcFilter(authorizingRealm));
    filters.put("logout", shiroLogoutFilter(authorizingRealm));
    filters.put("perms", shiroPermsFilter());
    filters.put("roles", shiroRolesFilter());
    filters.put("user", shiroUserFilter());
    FilterChainDefinitionMap chains = new FilterChainDefinitionMap();
    chains.setFilterChainDefinitions(Global.getProperty("shiro.filterChainDefinitions"));
    chains.setDefaultFilterChainDefinitions(Global.getProperty("shiro.defaultFilterChainDefinitions"));
    bean.setFilterChainDefinitionMap((Map)chains.getObject());
    return bean;
  }
  
  @Bean
  public AuthorizingRealm authorizingRealm(SessionDAO sessionDAO) {
    AuthorizingRealm bean = new AuthorizingRealm();
    bean.setSessionDAO(sessionDAO);
    return bean;
  }
  
  @Bean
  public CasOutHandler casOutHandler() {
    return new CasOutHandler();
  }
  
  @Bean
  public CasAuthorizingRealm casAuthorizingRealm(SessionDAO sessionDAO, CasOutHandler casOutHandler) {
    CasAuthorizingRealm bean = new CasAuthorizingRealm();
    bean.setSessionDAO(sessionDAO);
    bean.setCasOutHandler(casOutHandler);
    bean.setCasServerUrl(Global.getProperty("shiro.casServerUrl"));
    bean.setCasServerCallbackUrl(Global.getProperty("shiro.casClientUrl") + Global.getAdminPath() + "/login-cas");
    return bean;
  }
  
  @Bean
  public WebSecurityManager securityManager(AuthorizingRealm authorizingRealm, CasAuthorizingRealm casAuthorizingRealm, SessionManager sessionManager, CacheManager shiroCacheManager) {
    WebSecurityManager bean = new WebSecurityManager();
    Collection<Realm> realms = ListUtils.newArrayList();

    realms.add(authorizingRealm);
    realms.add(casAuthorizingRealm);
    
    realms.add(new FaceRealmAuthenticator());
    
    bean.setRealms(realms);
    bean.setSessionManager((SessionManager)sessionManager);
    bean.setCacheManager(shiroCacheManager);
    bean.setSubjectFactory((SubjectFactory)new CasSubjectFactory());
    return bean;
  }
  
  @Bean(name = {"lifecycleBeanPostProcessor"})
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }
  
  @Bean
  @DependsOn({"lifecycleBeanPostProcessor"})
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator bean = new DefaultAdvisorAutoProxyCreator();
    bean.setProxyTargetClass(true);
    return bean;
  }
  
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(WebSecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
    bean.setSecurityManager((SecurityManager)securityManager);
    return bean;
  }
}
