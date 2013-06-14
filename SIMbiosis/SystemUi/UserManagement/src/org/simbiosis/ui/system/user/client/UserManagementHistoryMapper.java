package org.simbiosis.ui.system.user.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.system.user.client.role.RoleListPlace;
import org.simbiosis.ui.system.user.client.user.UserListPlace;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ UserListPlace.Tokenizer.class, RoleListPlace.Tokenizer.class })
public interface UserManagementHistoryMapper extends KembangHistoryMapper {

}
