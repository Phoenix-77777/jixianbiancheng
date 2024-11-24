package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @PostMapping
    public Result save(@RequestBody Admin admin) {
        if (admin.getId() == null) {
            adminService.add(admin);
        } else {
            adminService.update(admin);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Admin> list = adminService.findAll();
        return Result.success(list);
    }
    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Admin> info = adminService.findBySearch(params);
        return Result.success(info);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        adminService.delete(id);
        return Result.success();
    }
    @GetMapping("/shoucang")
    public Result checkshoucang(Params params) {
        PageInfo<Admin> info = adminService.checkshoucang(params);
        return Result.success(info);
    }
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        List<Type> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Type.class);
        if (!CollectionUtil.isEmpty(infoList)) {
            for (Type type : infoList) {
                try {
                    typeService.add(type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.success();
    }
    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/files/**")
                .excludePathPatterns("/api/type/upload")
                .excludePathPatterns("/api/admin/login")
                .excludePathPatterns("/api/admin/register");
    }

    }

