package de.hepster.keycloak

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import org.keycloak.models.ClientSessionContext
import org.keycloak.models.KeycloakSession
import org.keycloak.models.ProtocolMapperModel
import org.keycloak.models.UserSessionModel
import org.keycloak.models.utils.ModelToRepresentation
import org.keycloak.protocol.ProtocolMapperUtils
import org.keycloak.protocol.oidc.mappers.*
import org.keycloak.protocol.oidc.mappers.GroupMembershipMapper.useFullPath
import org.keycloak.provider.ProviderConfigProperty
import org.keycloak.representations.IDToken
import java.util.*


class GroupMapper : AbstractOIDCProtocolMapper(), OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper {


    private val PROVIDER_ID = "exampleGroupMapper"
    private val configProperties: MutableList<ProviderConfigProperty> =
        ArrayList()

    init {
        val clientId = ProviderConfigProperty()
        OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties)
        OIDCAttributeMapperHelper.addJsonTypeConfig(configProperties)
        OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, GroupMapper::class.java)
    }

    override fun getId(): String {
        return PROVIDER_ID
    }


    override fun getDisplayCategory(): String {
        return "Servus Markus"
    }

    override fun getConfigProperties(): MutableList<ProviderConfigProperty> {
        return configProperties
    }

    override fun getDisplayType(): String {
        return "Group mapper"
    }

    override fun getHelpText(): String {
        return "Nu sag amol"
    }

    override fun setClaim(
        token: IDToken?,
        mappingModel: ProtocolMapperModel?,
        userSession: UserSessionModel?,
        keycloakSession: KeycloakSession?,
        clientSessionCtx: ClientSessionContext?
    ) {
        val membership: MutableList<Any> = LinkedList()
        for (group in userSession!!.user.groups) {
            membership.add(mapOf(ModelToRepresentation.buildGroupPath(group) to group.roleMappings.map { it.name }))
        }

        OIDCAttributeMapperHelper.mapClaim(
            token,
            mappingModel,
            JsonNodeFactory.instance.objectNode().putPOJO("groups", membership)
        )
    }

    fun createClaimMapper(
        name: String?,
        userAttribute: String?,
        tokenClaimName: String?, claimType: String?,
        accessToken: Boolean, idToken: Boolean, multivalued: Boolean
    ): ProtocolMapperModel? {
        val mapper = OIDCAttributeMapperHelper.createClaimMapper(
            name, userAttribute,
            tokenClaimName, claimType,
            accessToken, idToken,
            PROVIDER_ID
        )
        if (multivalued) {
            mapper.config[ProtocolMapperUtils.MULTIVALUED] = "true"
        }
        return mapper
    }


}