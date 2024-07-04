
import { Box, Button, TextField } from "@mui/material";
import { Formik } from "formik";
import * as yup from "yup";
import useMediaQuery from "@mui/material/useMediaQuery";
import Header from "../../components/Header";

const initialValues = {
  name: "",
  email: "",
  contactLastName: "",
  contactFirstName: "",
  phone: "",
  addressLine1: "",
  addressLine2: "",
  city: "",
  state: "",
  postalCode: "",
  country: "",
  creditLimit: "",
  employeeId: "",
}

const phoneRegExp =
  /^((\+[1-9]{1,4}[ -]?)|(\([0-9]{2,3}\)[ -]?)|([0-9]{2,4})[ -]?)*?[0-9]{3,4}[ -]?[0-9]{3,4}$/;

const customersSchema = yup.object().shape({
  name: yup.string().max(50).required(),
  email: yup.string().email().max(50).required(),
  contactLastName: yup.string().max(50).required(),
  // contactFirstName: yup.string().max(50, "Max 50 characters").required(),
  // phone: yup
  //   .string()
  //   .matches(phoneRegExp, "Phone number is not valid")
  //   .required(),
  // address1: yup.string().max(50, "Max 50 characters").required(),
  // address2: yup.string().max(50, "Max 50 characters"),
  // city: yup.string().max(50, "Max 50 characters").required(),
  // state: yup.string().max(50, "Max 50 characters"),
  // postalCode: yup.string().max(15, "Max 15 characters"),
  // country: yup.string().max(50, "Max 50 characters").required(),
  // creditLimit: yup.number().positive().max(9999.99, "Exceeded max limit").required,
  // employeeId: yup.number().positive(),
})

const FormAddCustomer = () => {
  const isNonMobile = useMediaQuery("(min-width:600px)");

  const handleFormSubmit = (values) => {
    console.log(values);
  }

  return <Box m="20px">
    <Header title="CREATE CUSTOMER" subtitle="Create a new Customer" />

    <Formik
      onSubmit={handleFormSubmit}
      initialValues={initialValues}
      validationSchema={customersSchema}
    >
      {({ values, errors, touched, handleBlur, handleChange, handleSubmit }) => (
        <form onSubmit={handleSubmit}>
          <Box
            display="grid"
            gap="30px"
            gridTemplateColumns="repeat(4, minmax(0, 1fr))"
            sx={{
              "& > div": { gridColumn: isNonMobile ? undefined : "span 4" },
            }}
          >
            <TextField
              fullWidth
              variant="filled"
              type="text"
              label="Name"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.name}
              name="name"
              error={!!touched.name && !!errors.name}
              helperText={touched.name && errors.name}
              sx={{ gridColumn: "span 2" }}
            />
            <TextField
              fullWidth
              variant="filled"
              type="text"
              label="Email"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.email}
              name="email"
              error={!!touched.email && !!errors.email}
              helperText={touched.email && errors.email}
              sx={{ gridColumn: "span 4" }}
            />
            <TextField
              fullWidth
              variant="filled"
              type="text"
              label="Contact Last Name"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.contactLastName}
              name="contactLastName"
              error={!!touched.contactLastName && !!errors.contactLastName}
              helperText={touched.contactLastName && errors.contactLastName}
              sx={{ gridColumn: "span 2" }}
            />
            <TextField
              fullWidth
              variant="filled"
              type="text"
              label="Contact First Name"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.contactFirstName}
              name="contactFirstName"
              error={!!touched.contactFirstName && !!errors.contactFirstName}
              helperText={touched.contactFirstName && errors.contactFirstName}
              sx={{ gridColumn: "span 4" }}
            />
            <TextField
              fullWidth
              variant="filled"
              type="text"
              label="Phone"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.phone}
              name="phone"
              error={!!touched.phone && !!errors.phone}
              helperText={touched.phone && errors.phone}
              sx={{ gridColumn: "span 4" }}
            />
            <TextField
              fullWidth
              variant="filled"
              type="text"
              label="Address Line 1"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.addressLine1}
              name="addressLine1"
              error={!!touched.addressLine1 && !!errors.addressLine1}
              helperText={touched.addressLine1 && errors.addressLine1}
              sx={{ gridColumn: "span 4" }}
            />
            <TextField
              fullWidth
              variant="filled"
              type="text"
              label="Address Line 2"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.addressLine2}
              name="addressLine2"
              error={!!touched.addressLine2 && !!errors.addressLine2}
              helperText={touched.addressLine2 && errors.addressLine2}
              sx={{ gridColumn: "span 4" }}
            />
          </Box>
          <Box display="flex" justifyContent="end" mt="20px">
            <Button type="submit" color="secondary" variant="contained">
              Create New Customer
            </Button>
          </Box>
        </form>
      )}
    </Formik>
  </Box>
}

export default FormAddCustomer;
