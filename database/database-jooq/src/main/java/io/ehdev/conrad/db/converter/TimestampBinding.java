package io.ehdev.conrad.db.converter;

import org.jooq.*;
import org.jooq.impl.DefaultBinding;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class TimestampBinding implements Binding<Timestamp, Instant> {

    private static final Converter<Timestamp, Instant> converter = new TimestampConverter();

    private final DefaultBinding<Timestamp, Instant> delegate =
        new DefaultBinding<>(converter());

    @Override public Converter<Timestamp, Instant> converter() { return converter; }

    @Override public void sql(BindingSQLContext<Instant> ctx) throws SQLException {
        delegate.sql(ctx);
    }

    @Override
    public void register(BindingRegisterContext<Instant> ctx) throws SQLException {
        delegate.register(ctx);
    }

    @Override
    public void set(BindingSetStatementContext<Instant> ctx) throws SQLException {
        delegate.set(ctx);
    }

    @Override
    public void set(BindingSetSQLOutputContext<Instant> ctx) throws SQLException {
        delegate.set(ctx);
    }

    @Override
    public void get(BindingGetResultSetContext<Instant> ctx) throws SQLException {
        delegate.get(ctx);
    }

    @Override
    public void get(BindingGetStatementContext<Instant> ctx) throws SQLException {
        delegate.get(ctx);
    }

    @Override
    public void get(BindingGetSQLInputContext<Instant> ctx) throws SQLException {
        delegate.get(ctx);
    }
}
