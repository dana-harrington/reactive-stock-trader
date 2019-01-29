package com.redelastic.stockbroker.wireTransfer.impl.transfer;

import akka.NotUsed;
import akka.japi.Pair;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.Offset;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.redelastic.stocktrader.TransferId;

import javax.inject.Inject;

public class TransferRepository {

    private final PersistentEntityRegistry entityRegistry;

    @Inject
    public TransferRepository(PersistentEntityRegistry entityRegistry) {
        entityRegistry.register(TransferEntity.class);
        this.entityRegistry = entityRegistry;
    }

    public PersistentEntityRef<TransferCommand> get(TransferId transferId) {
        return entityRegistry.refFor(TransferEntity.class, transferId.getId());
    }

    public Source<Pair<TransferEvent, Offset>, NotUsed> eventStream(AggregateEventTag<TransferEvent> tag, Offset offset) {
        return entityRegistry.eventStream(tag, offset);
    }
}
