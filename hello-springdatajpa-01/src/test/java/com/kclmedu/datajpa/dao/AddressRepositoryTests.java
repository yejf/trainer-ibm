package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.BaseTests;
import com.kclmedu.datajpa.entity.Address;
import com.kclmedu.datajpa.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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

    @Test
    public void testFindById() {
        Optional<Address> address = addressRepository.findById("4028b8816d825184016d8251a1200000");
        System.out.println(address.get());
        System.out.println(address.get().getUser());
    }

    @Test
    public void testDeleteById() {
        addressRepository.deleteById("4028b8816d82533b016d825357e50000");
    }

    @Test
    public void testDelete() {
        Address a = new Address();
        a.setId("4028b8816d825184016d8251a1200000");
        addressRepository.delete(a);
    }

    @Test
    public void testFindByUserName() {
        List<Address> addressList = addressRepository.findByUserName("jack");
        //
        if(addressList != null) {
            addressList.forEach(System.out::println);
        }
    }
}
