<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<i-header> 
        <i-menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo">zhb</div>
          <div class="layout-nav" style="text-align: right;">
              <Menu-item name="3" :to="personalinfo"> <Icon type="ios-person"></Icon> 信息 </Menu-item>
              <Menu-item name="4" :to="layouturl"> <Icon type="ios-log-out"></Icon> 退出 </Menu-item>
          </div>
        </i-menu> 
</i-header>