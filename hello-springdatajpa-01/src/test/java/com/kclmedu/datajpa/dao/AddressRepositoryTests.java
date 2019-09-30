package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.BaseTests;
import com.kclmedu.datajpa.entity.Address;
import com.kclmedu.datajpa.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AddressRepositoryTests extends BaseTests {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() {
        Address a = new Address();
        a.setState("中国");
        a.setProvince("江苏省");
        a.setCity("南京市");
        a.setDetail("北京西路189号xxx大厦5楼");
        a.setReceiver("张无记");
        a.setPhone("13877669922");
        //
        Optional<User> user = userRepository.findById("4028b8816d823659016d823674a90000");
        a.setUser(user.get());

        //
        addressRepository.save(a);
    }
}
