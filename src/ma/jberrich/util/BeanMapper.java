package ma.jberrich.util;

import java.util.ArrayList;
import java.util.List;

import ma.jberrich.bean.Dept;
import ma.jberrich.bean.Emp;
import ma.jberrich.model.Employe;
import ma.jberrich.model.Service;
import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.DozerBeanMapperSingletonWrapper;

public class BeanMapper {

	private static BeanMapper bean;

	private DozerBeanMapper mapper;

	private BeanMapper() {
		init();
	}

	private void init() {
		List<String> mappingFiles = new ArrayList<String>();
		mappingFiles.add("bean-mappings.xml");

		mapper = (DozerBeanMapper) DozerBeanMapperSingletonWrapper.getInstance();
		mapper.setMappingFiles(mappingFiles);
	}

	public static BeanMapper getInstance() {
		if (bean == null) {
			bean = new BeanMapper();
			return bean;
		} else {
			return bean;
		}
	}
	
	public Service map(Dept source) {
        Service destination = (Service) mapper.map(source, Service.class);
        return destination;
	}
	
	public Employe map(Emp source) {
        Employe destination = (Employe) mapper.map(source, Employe.class);
        return destination;
	}

}
