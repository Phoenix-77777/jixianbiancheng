package com.example.service;

import com.example.common.Result;
import com.example.dao.AdminDao;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;

import java.util.List;


@Service
public class AdminService {

    @Resource
    private AdminDao adminDao;

    public List<Admin> findAll() {

        return adminDao.selectAll();
    }

    public PageInfo<Admin> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Admin> list = adminDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
        adminDao.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }

    public void delete(Integer id) {
        adminDao.deleteByPrimaryKey(id);
    }

    public PageInfo<Admin> checkshoucang(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Admin> list = adminDao.checkshoucang(params);
        return PageInfo.of(list);
    }
}
