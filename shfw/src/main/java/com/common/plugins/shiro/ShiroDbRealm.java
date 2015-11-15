
package com.common.plugins.shiro;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

public class ShiroDbRealm extends org.apache.shiro.realm.jdbc.JdbcRealm {
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
			AuthenticationInfo info = null;
		try{
			
			
			info = super.doGetAuthenticationInfo(token);
			return info;
		}finally{
			if(null==info){
				//LOG
			}
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		AuthorizationInfo info =super.doGetAuthorizationInfo(principals);
		
		Collection<String> permissions = info.getStringPermissions();//解决NULL为题
		if(permissions.contains("null")){
			permissions.remove("null");
		}
		if(permissions.contains("")){
			permissions.remove("");
		}
		return info;
	}


}
