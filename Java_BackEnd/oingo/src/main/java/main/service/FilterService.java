package main.service;

import main.dao.FestivalDao;
import main.dao.FilterDao;
import main.model.Festival;
import main.model.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("FilterService")
public class FilterService {

    @Autowired
    private FilterDao filterDao;

    @Autowired
    private FestivalDao festivalDao;

    public Filter getFilter(int fid){
        return filterDao.listFilter(fid);
    }

    public int addFilter(Filter filter){
        filterDao.addFilter(filter);
        return filterDao.listFilter(-1).getFid();
    }

    public List<Festival> listFestival(){
        return festivalDao.listFestival(-2);
    }
}
