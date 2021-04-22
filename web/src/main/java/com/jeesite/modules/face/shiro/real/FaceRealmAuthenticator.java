package com.jeesite.modules.face.shiro.real;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.shell.ShellCommandInitException;

import com.jeesite.common.config.Global;
import com.jeesite.common.lang.ObjectUtils;
import com.jeesite.common.reflect.ClassUtils;
import com.jeesite.common.shiro.realm.AuthorizingRealmImpl;
import com.jeesite.common.shiro.realm.BaseAuthorizingRealm;
import com.jeesite.common.shiro.realm.LoginInfo;
import com.jeesite.modules.face.shiro.token.FaceToken;
import com.jeesite.modules.sys.entity.Menu;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;

public class FaceRealmAuthenticator extends AuthorizingRealmImpl {

	public FaceRealmAuthenticator() {
		setCredentialsMatcher(new CredentialsMatcher() {
			@Override
			public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		setAuthenticationTokenClass(FaceToken.class);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		
		FaceToken token = (FaceToken) authcToken; 
	    try {
	       String userName = token.getUsername().trim(); 
	       User user = UserUtils.get(userName);
	       Subject currentUser = SecurityUtils.getSubject();
	       
	       LoginInfo loginInfo = new LoginInfo(user,new HashMap()); //com.jeesite.modules.sys.utils.UserUtils.getLoginInfo();
	       //getPrincipal	//principal
	     
	    
	       SimpleAuthenticationInfo ret = new SimpleAuthenticationInfo(loginInfo, "", null, this.getName());
	       
	       
	       
	       
	       SimpleAuthorizationInfo a = new SimpleAuthorizationInfo();
	       Object l = com.jeesite.modules.sys.utils.UserUtils.getAuthInfo();
	       for (Role role : user.getRoleList()) {
	    	   a.addRole(role.getRoleCode());
	       }
	       for (Menu menu :  UserUtils.getMenuList()) {
	    	   if(!StringUtils.isEmpty(menu.getMenuCode()))
	    		   a.addStringPermission(menu.getMenuCode());
	       } 
	       com.jeesite.modules.sys.utils.UserUtils.putCache("authInfo_"+com.jeesite.modules.sys.utils.UserUtils.getSession().getId(), (Object)a);

           return (AuthenticationInfo)ret;
	      
	    }
	    catch(Exception e) {
	       //log.info("login failed:" + e.getMessage());
	       e.printStackTrace();
	    } 
	    return null;
	}
	
	
	
	public boolean validatePassword(String plainPassword, String password) {
		return true;
	}
	
	/*
	public void test(PrincipalCollection a) {
        final User value;
        if ((value = UserUtils.get(((LoginInfo)(a = (PrincipalCollection)this.getAvailablePrincipal(a))).getId())) == null) {
            return null;
        }
        final Subject subject = UserUtils.getSubject();
        final Session session = UserUtils.getSession();
        final PrincipalCollection collection = a;
        final String s = "]]OQZ]mAI]";
        final String s2 = "i'x8D)g-";
        final Session session2 = session;
        final String s3 = "ZWKHzW]]";
        final String s4 = "\u007f;o:^1z-";
        final Session session3 = session;
        final String s5 = "LK\\JwYT]";
        session.setAttribute((Object)ShellCommandInitException.final((Object)"\u007f;o:I'n-"), (Object)value.getUserCode());
        session3.setAttribute((Object)ProcCpu.final((Object)s5), (Object)value.getUserName());
        session3.setAttribute((Object)ShellCommandInitException.final((Object)s4), (Object)value.getUserType());
        session2.setAttribute((Object)ProcCpu.final((Object)s3), (Object)value.getCorpCode_());
        session2.setAttribute((Object)ShellCommandInitException.final((Object)s2), (Object)value.getCorpName_());
        final String param;
        if (StringUtils.isNotBlank((CharSequence)(param = ((LoginInfo)collection).getParam(ProcCpu.final((Object)s))))) {
            final long longValue = ObjectUtils.toLong((Object)Global.getProperty(ShellCommandInitException.final((Object)"y-y;c'dfy-y;c'd\u001cc%o'\u007f<")));
            final long longValue2 = ObjectUtils.toLong((Object)Global.getProperty(new StringBuilder().insert(0, ProcCpu.final((Object)"J]JKPWW\u0016")).append(param).append(ShellCommandInitException.final((Object)"Y-y;c'd\u001cc%o'\u007f<")).toString()));
            session.setTimeout((longValue2 > 0L) ? longValue2 : longValue);
            session.setAttribute((Object)ProcCpu.final((Object)"]]OQZ]mAI]"), (Object)param);
        }
        if (StringUtils.isBlank((CharSequence)ObjectUtils.toString(session.getAttribute((Object)ShellCommandInitException.final((Object)";s;I'n-"))))) {
            session.setAttribute((Object)ShellCommandInitException.final((Object)";s;I'n-"), (Object)((LoginInfo)a).getParam(ProcCpu.final((Object)"K@KzW]]"), (String)null));
        }
        if (StringUtils.isBlank((CharSequence)ObjectUtils.toString(session.getAttribute((Object)ProcCpu.final((Object)"KWU]zW]]"))))) {
            session.setAttribute((Object)ProcCpu.final((Object)"KWU]zW]]"), (Object)((LoginInfo)a).getParam(ShellCommandInitException.final((Object)"x'f-I'n-"), (String)null));
        }
        if (("0".equals(I.float().get((Object)"type")) || "9".equals(I.float().get((Object)"type"))) && (I.new() >= 4 + ("9".equals(I.float().get((Object)"type")) ? 0 : 17) || ra.null || ("9".equals(I.float().get((Object)"type")) && (System.currentTimeMillis() - I.final) / 64800000L > 0L))) {
            return null;
        }
        final PrincipalCollection loginInfo = a;
        final Subject subject2 = subject;
        final User user = value;
        this.multiAddrLoginCheck((LoginInfo)a, subject, session, value);
        BaseAuthorizingRealm.isValidCodeLogin(user.getLoginCode(), value.getCorpCode_(), param, ShellCommandInitException.final((Object)";\u007f+i-y;"));
        return this.doGetAuthorizationInfo((LoginInfo)loginInfo, subject2, session, value);
        
	}
	*/
	
	 
}
