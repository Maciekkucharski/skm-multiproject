package pl.edu.pjwstk.skmapi.services;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.skmapi.exception.EmptyFieldException;

import java.util.List;
import java.util.Optional;

public abstract class CrudService<T extends DbEntity> {
    JpaRepository<T, Long> repository;

    public CrudService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
//        int page, int size, String[] sort
//        Pageable pageable;
//        if (sort[0].equals("asc")) {
//            pageable = PageRequest.of(page, size, Sort.by(sort[1]).ascending());
//        } else {
//            pageable = PageRequest.of(page, size, Sort.by(sort[1]).descending());
//        }
//        Iterable<T> items = repository.findAll(pageable);
//        var itemList = new ArrayList<T>();
//
//        items.forEach(itemList::add);
//
//        return itemList;
        return repository.findAll();
    }

    public T getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new idNotFoundException("not a valid id"));
    }

    public void delete(Long id) {
        Optional<T> item = repository.findById(id);
        repository.delete(item.orElseThrow(() -> new idNotFoundException("not a valid id")));
    }

    public abstract T createOrUpdate(T updateEntity) throws EmptyFieldException;


}