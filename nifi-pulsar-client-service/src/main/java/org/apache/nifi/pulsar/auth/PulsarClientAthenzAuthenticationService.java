/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.pulsar.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.processor.util.StandardValidators;
import org.apache.pulsar.client.api.Authentication;
import org.apache.pulsar.client.api.AuthenticationFactory;

// import org.apache.pulsar.client.impl.auth.AuthenticationAthenz;

/**
 * https://pulsar.apache.org/docs/en/security-athenz/
 *
 */
public class PulsarClientAthenzAuthenticationService extends AbstractPulsarClientAuntenticationService {
	
    public static final PropertyDescriptor TENANT_DOMAIN = new PropertyDescriptor.Builder()
            .name("The tenant domain name")
            .description("The domain name for this tenant")
            .defaultValue(null)
            .addValidator(StandardValidators.NON_BLANK_VALIDATOR)
            .sensitive(false)
            .build();
    
    public static final PropertyDescriptor TENANT_SERVICE = new PropertyDescriptor.Builder()
            .name("The tenant service name")
            .description("The service name for this tenant")
            .defaultValue(null)
            .addValidator(StandardValidators.NON_BLANK_VALIDATOR)
            .sensitive(false)
            .build();
	
    public static final PropertyDescriptor PROVIDER_DOMAIN = new PropertyDescriptor.Builder()
            .name("The provider domain")
            .description("The provider domain name")
            .defaultValue(null)
            .addValidator(StandardValidators.NON_BLANK_VALIDATOR)
            .sensitive(false)
            .build();
    
    public static final PropertyDescriptor TENANT_PRIVATE_KEY_FILE = new PropertyDescriptor.Builder()
            .name("Tenants Private Key Filename")
            .description("The fully-qualified filename of the tenant's private key.")
            .defaultValue(null)
            .addValidator(createFileExistsAndReadableValidator())
            .sensitive(false)
            .build();
    
    public static final PropertyDescriptor TENANT_PRIVATE_KEY_ID = new PropertyDescriptor.Builder()
            .name("Tenants Private Key Id")
            .description("The id of tenant's private key.")
            .defaultValue("0")
            .required(false)
            .sensitive(false)
            .build();
    
    private static final List<PropertyDescriptor> properties;
    
    static {
        List<PropertyDescriptor> props = new ArrayList<>();
        props.add(TRUST_CERTIFICATE);
        props.add(TENANT_DOMAIN);
        props.add(TENANT_SERVICE);
        props.add(PROVIDER_DOMAIN);
        props.add(TENANT_PRIVATE_KEY_FILE);
        props.add(TENANT_PRIVATE_KEY_ID);
        properties = Collections.unmodifiableList(props);
    }
    
    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return properties;
    }

	@Override
	public Authentication getAuthentication() {
		Map<String, String> authParams = new HashMap<>();
		// TODO Define constants for these keys
		authParams.put("tenantDomain", configContext.getProperty(TENANT_DOMAIN).getValue()); 
		authParams.put("tenantService", configContext.getProperty(TENANT_SERVICE).getValue()); 
		authParams.put("providerDomain", configContext.getProperty(PROVIDER_DOMAIN).getValue()); 
		authParams.put("privateKey", configContext.getProperty(TENANT_PRIVATE_KEY_FILE).getValue()); 
		
		if (configContext.getProperty(TENANT_PRIVATE_KEY_ID).isSet()) {
		   authParams.put("keyId", configContext.getProperty(TENANT_PRIVATE_KEY_ID).getValue()); 
		}

 //		return AuthenticationFactory.create(AuthenticationAthenz.class.getName(), authParams);

		return null;
	}

}
