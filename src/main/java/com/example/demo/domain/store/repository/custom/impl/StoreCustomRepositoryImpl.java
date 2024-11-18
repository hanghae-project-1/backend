package com.example.demo.domain.store.repository.custom.impl;

import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.repository.custom.StoreCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.example.demo.domain.store.entity.QStore.store;

@Repository
@RequiredArgsConstructor
public class StoreCustomRepositoryImpl implements StoreCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<Store> searchByFilters(UUID categoryId, UUID regionId){

        BooleanBuilder builder = new BooleanBuilder();

        if (categoryId != null && regionId == null) {
            builder.and(store.categoryMenu.id.eq(categoryId));
        }

        if (regionId != null && categoryId == null){
            builder.and(store.region.id.eq(regionId));
        }

        if (regionId != null && categoryId != null) {
            builder.and(store.categoryMenu.id.eq(categoryId)
                    .and(store.region.id.eq(regionId)));
        }

        return queryFactory
                .selectFrom(store)
                .where(builder)
                .fetch();

    }

    public List<Store> searchStoreByOwner(String ownerName, String keyWord){

        BooleanBuilder builder = new BooleanBuilder();

        if (keyWord != null){
            builder.and(store.name.containsIgnoreCase(keyWord));
        }

        return queryFactory
                .selectFrom(store)
                .where(store.ownerName.eq(ownerName).and(builder))
                .fetch();
    }

}
