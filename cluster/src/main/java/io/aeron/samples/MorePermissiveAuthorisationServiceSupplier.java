/*
 * Copyright 2023 Adaptive Financial Consulting
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.aeron.samples;

import io.aeron.cluster.ConsensusModule;
import io.aeron.cluster.codecs.HeartbeatRequestDecoder;
import io.aeron.cluster.codecs.StandbySnapshotDecoder;
import io.aeron.security.AuthorisationService;
import io.aeron.security.AuthorisationServiceSupplier;

final class MorePermissiveAuthorisationServiceSupplier implements AuthorisationServiceSupplier
{
    public static final MorePermissiveAuthorisationServiceSupplier INSTANCE =
        new MorePermissiveAuthorisationServiceSupplier();

    private MorePermissiveAuthorisationServiceSupplier()
    {
        // No-op
    }

    @Override
    public AuthorisationService get()
    {
        return DefaultAuthorisationService.INSTANCE;
    }

    private static final class DefaultAuthorisationService implements AuthorisationService
    {
        private static final DefaultAuthorisationService INSTANCE = new DefaultAuthorisationService();

        private static final AuthorisationService DELEGATE =
            ConsensusModule.Configuration.DEFAULT_AUTHORISATION_SERVICE_SUPPLIER.get();

        private DefaultAuthorisationService()
        {
        }

        @Override
        public boolean isAuthorised(
            final int protocolId,
            final int actionId,
            final Object type,
            final byte[] encodedPrincipal
        )
        {
            return HeartbeatRequestDecoder.SCHEMA_ID == protocolId && HeartbeatRequestDecoder.TEMPLATE_ID == actionId ||
                StandbySnapshotDecoder.SCHEMA_ID == protocolId && StandbySnapshotDecoder.TEMPLATE_ID == actionId ||
                DELEGATE.isAuthorised(protocolId, actionId, type, encodedPrincipal);
        }
    }
}
