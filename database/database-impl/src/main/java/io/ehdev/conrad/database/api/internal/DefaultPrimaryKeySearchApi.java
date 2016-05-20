package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.PrimaryKeySearchApi;
import io.ehdev.conrad.database.model.PrimaryResourceData;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.ResourceDetailLookupTable;
import io.ehdev.conrad.db.tables.records.ResourceDetailLookupRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultPrimaryKeySearchApi implements PrimaryKeySearchApi {

    private final DSLContext dslContext;

    @Autowired
    public DefaultPrimaryKeySearchApi(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Optional<PrimaryResourceData> findResourceDataByProjectId(UUID projectId) {
        ResourceDetailLookupTable detailLookupTable = Tables.RESOURCE_DETAIL_LOOKUP;

        Record record = dslContext
            .select()
            .from(detailLookupTable)
            .where(detailLookupTable.PROJECT_UUID.eq(projectId))
            .and(detailLookupTable.REPO_UUID.isNull())
            .limit(1)
            .fetchOne();

        if(record == null) {
            return Optional.empty();
        }

        ResourceDetailLookupRecord into = record.into(Tables.RESOURCE_DETAIL_LOOKUP);
        return Optional.of(new PrimaryResourceData(into.getProjectName(), into.getProjectUuid(), into.getRepoName(), into.getRepoUuid()));
    }

    @Override
    public Optional<PrimaryResourceData> findResourceDataByRepoId(UUID repoId) {
        ResourceDetailLookupTable detailLookupTable = Tables.RESOURCE_DETAIL_LOOKUP;

        Record record = dslContext
            .select()
            .from(detailLookupTable)
            .where(detailLookupTable.REPO_UUID.eq(repoId))
            .limit(1)
            .fetchOne();

        if(record == null) {
            return Optional.empty();
        }

        ResourceDetailLookupRecord into = record.into(Tables.RESOURCE_DETAIL_LOOKUP);
        return Optional.of(new PrimaryResourceData(into.getProjectName(), into.getProjectUuid(), into.getRepoName(), into.getRepoUuid()));
    }

    @Override
    public Optional<UUID> findProjectId(String projectName) {
        ResourceDetailLookupTable detailLookupTable = Tables.RESOURCE_DETAIL_LOOKUP;

        Record1<UUID> record = dslContext
            .select(detailLookupTable.PROJECT_UUID)
            .from(detailLookupTable)
            .where(detailLookupTable.PROJECT_NAME.eq(projectName))
            .and(detailLookupTable.REPO_UUID.isNull())
            .limit(1)
            .fetchOne();

        return Optional.ofNullable(record).map(Record1::value1);
    }

    @Override
    public Optional<UUID> findRepoId(String projectName, String repoName) {
        ResourceDetailLookupTable detailLookupTable = Tables.RESOURCE_DETAIL_LOOKUP;

        Record1<UUID> record = dslContext
            .select(detailLookupTable.REPO_UUID)
            .from(detailLookupTable)
            .where(detailLookupTable.PROJECT_NAME.eq(projectName))
            .and(detailLookupTable.REPO_NAME.eq(repoName))
            .limit(1)
            .fetchOne();

        return Optional.ofNullable(record).map(Record1::value1);
    }
}
