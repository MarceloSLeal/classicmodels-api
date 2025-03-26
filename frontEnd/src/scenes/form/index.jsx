import { Box, Button, FormControl, Select, TextField, InputLabel, MenuItem } from "@mui/material";
import { Formik } from "formik";
import * as yup from "yup";
import useMediaQuery from "@mui/material/useMediaQuery";
import Header from "../../components/Header";

const initialValues = {
  login: "",
  password: "",
  role: "",
}

const userSchema = yup.object().shape({
  login: yup.string().max(50).required("required"),
  password: yup.string().max(10).required("required"),
  passwordConfirm: yup.string().oneOf([yup.ref("password"), null], "Passwords must match"),
  role: yup.string().required("required"),
});

const Form = () => {
  const isNonMobile = useMediaQuery("(min-width:600px)");

  const handleFormSubmit = (values) => {
    console.log(values);
  }

  return <Box m="20px">
    <Header title="CREATE USER" subtitle="Create a new User Profile" />

    <Formik
      onSubmit={handleFormSubmit}
      initialValues={initialValues}
      validationSchema={userSchema}
    >
      {({ values, errors, touched, handleBlur, handleChange, handleSubmit }) => (
        <form onSubmit={handleSubmit}>
          <Box
            display="grid"
            gap="30px"
            gridTemplateColumns="repeat(5, minmax(0, 1fr))"
            sx={{
              "& > div": { gridColumn: isNonMobile ? undefined : "span 5" },
            }}
          >
            <TextField
              fullWidth
              variant="filled"
              type="text"
              label="Login"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.login}
              name="login"
              error={!!touched.login && !!errors.login}
              helperText={touched.login && errors.login}
              sx={{ gridColumn: "span 2" }}
            />

            <Box sx={{ gridColumn: "span 3" }} />

            <TextField
              fullWidth
              variant="filled"
              type="password"
              label="Password"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.password}
              name="password"
              error={!!touched.password && !!errors.password}
              helperText={touched.password && errors.password}
              sx={{ gridColumn: "span 1" }}
            />

            <TextField
              fullWidth
              variant="filled"
              type="password"
              label="Confirm Password"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.passwordConfirm}
              name="passwordConfirm"
              error={!!touched.passwordConfirm && !!errors.passwordConfirm}
              helperText={touched.passwordConfirm && errors.passwordConfirm}
              sx={{ gridColumn: "span 1" }}
            />

            <Box sx={{ gridColumn: "span 3" }} />

            <FormControl
              variant="filled"
              sx={{ gridColumn: "span 2" }}
            >
              <InputLabel id="user-select-role">Role </InputLabel>
              <Select
                labelId="user-select-role"
                id="user-role"
                name="userRole"
                value={values.role}
                onChange={(event) => setFieldValue('userRole', event.target.value)}
                onBlur={handleBlur}
                label="User Role"
              >
                <MenuItem value="">
                  <em>None</em>
                </MenuItem>
                <MenuItem key="USER" value="USER" />
                <MenuItem key="ADMIN" value="ADMIN" />

                {/* {dataEmployeeIdNameList && dataEmployeeIdNameList.map((employee) => ( */}
                {/*   <MenuItem key={employee.id} value={employee.id}> */}
                {/*     {employee.id} */}
                {/*     {" "} {employee.lastName} {employee.firstName} */}
                {/*     {" "} - {employee.jobTitle} */}
                {/*   </MenuItem> */}
                {/* ))} */}
              </Select>
            </FormControl>


          </Box>
          <Box display="flex" justifyContent="end" mt="20px">
            <Button type="submit" color="secondary" variant="contained">
              Create New user
            </Button>
          </Box>
        </form>
      )}
    </Formik>
  </Box>
}

export default Form;
