package com.ClearSolutions.testassig;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class TestAssigApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("create user")
	public void testCreateUser() throws Exception {
		String userJson = "{\"email\": \"test@example.com\", " +
				"\"firstName\": \"S1\", " +
				"\"lastName\": \"s1\", " +
				"\"birthDate\": \"2000-01-01\"}";

		ResultMatcher created = MockMvcResultMatchers.status().isCreated();

		this.mockMvc.perform(MockMvcRequestBuilders
						.post("/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(created);
	}

	@Test
	@DisplayName("create user unsuccessful - less age")
	public void testCantCreateUserAge() throws Exception { // test age < 18
		String userJson = "{\"email\": \"wrongemail\", " +
				"\"firstName\": \"S1\", " +
				"\"lastName\": \"s1\", " +
				"\"birthDate\": \"2018-01-01\"}";

		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		this.mockMvc.perform(MockMvcRequestBuilders
						.post("/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(badRequest);
	}

	@Test
	@DisplayName("create user unsuccessful - wrong email")
	public void testCantCreateUserEmail() throws Exception { // wrong email
		String userJson = "{\"email\": \"test@example.com\", " +
				"\"firstName\": \"S1\", " +
				"\"lastName\": \"s1\", " +
				"\"birthDate\": \"2018-01-01\"}";

		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		this.mockMvc.perform(MockMvcRequestBuilders
						.post("/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(badRequest);
	}

	@Test
	@DisplayName("create user unsuccessful - null or empty fields")
	public void testCantCreateUserNullField() throws Exception {
		String userJson = "{\"email\": \"test@example.com\", " +
				"\"birthDate\": \"2018-01-01\"}";

		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		this.mockMvc.perform(MockMvcRequestBuilders
						.post("/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(badRequest);
	}

	//=======================================================================
	@Test
	@DisplayName("update user - one field")
	public void testUpdateOneField() throws Exception {
		String userJson = "{\"id\":2,\"address\": \"UpdateADDRESS\"}";

		ResultMatcher updated = MockMvcResultMatchers.status().isOk();

		this.mockMvc.perform(MockMvcRequestBuilders
						.put("/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(updated);
	}

	@Test
	@DisplayName("update user - some fields")
	public void testUpdateSomeFields() throws Exception {
		String userJson = "{\"id\":2,\"address\": \"UpdateADDRESS\", \"phoneNumber\":\"09633333333\"}";

		ResultMatcher updated = MockMvcResultMatchers.status().isOk();

		this.mockMvc.perform(MockMvcRequestBuilders
						.put("/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(updated);
	}

	@Test
	@DisplayName("update user - all fields")
	public void testUpdateAllFields() throws Exception {
		String userJson = "{\"id\":2," +
				"\"email\": \"updateS@example.com\"," +
				" \"firstName\": \"T1\"," +
				" \"lastName\": \"T1\"," +
				" \"birthDate\": \"2000-01-01\"," +
				"\"address\": \"UpdateADDRESS\", " +
				"\"phoneNumber\":\"09633333333\"}";

		ResultMatcher updated = MockMvcResultMatchers.status().isOk();

		this.mockMvc.perform(MockMvcRequestBuilders
						.put("/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(updated);
	}

	@Test
	@DisplayName("unsuccessful update user - update to null")
	public void testNotUpdateToNull() throws Exception {
		String userJson = "{\"id\":2,\"email\": null}";
													// .isOk() so Controller catches null requests and gives status 200
		ResultMatcher notUpdated = MockMvcResultMatchers.status().isOk();
		this.mockMvc.perform(MockMvcRequestBuilders
						.put("/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(notUpdated);
	}

	@Test
	@DisplayName("unsuccessful update user - less age")
	public void testNotUpdateToLessAge() throws Exception {
		String userJson = "{\"id\":2,\"birthDate\":\"2018-10-10\"}";

		ResultMatcher notUpdated = MockMvcResultMatchers.status().isBadRequest();
		this.mockMvc.perform(MockMvcRequestBuilders
						.put("/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(notUpdated);
	}

	@Test
	@DisplayName("unsuccessful update user - wrong email")
	public void testNotUpdateWrongEmail() throws Exception {
		String userJson = "{\"id\":2,\"email\": \"wrongEmail\"}";

		ResultMatcher notUpdated = MockMvcResultMatchers.status().isBadRequest();
		this.mockMvc.perform(MockMvcRequestBuilders
						.put("/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(notUpdated);
	}
	// ====================================================================

	@Test
	@DisplayName("search users")
	public void testSearchUsers() throws Exception {
		String userJson = "{\"date_from\":\"1999-10-10\", \"date_to\":\"2001-10-10\"}";

		ResultMatcher getSearch = MockMvcResultMatchers.status().isOk();

		this.mockMvc.perform(MockMvcRequestBuilders
						.get("/search")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(getSearch);
	}

	@Test
	@DisplayName("search no users")
	public void testSearchNoUsers() throws Exception {
		String userJson = "{\"date_from\":\"1998-10-10\", \"date_to\":\"1999-10-10\"}";

		ResultMatcher getSearch = MockMvcResultMatchers.status().isOk();

		this.mockMvc.perform(MockMvcRequestBuilders
						.get("/search")
						.contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(getSearch);
	}
}
