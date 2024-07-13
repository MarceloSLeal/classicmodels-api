import { TextField } from "@mui/material";

const CustomersFormEditInputs = ({ rowData, handleBlur, handleChange, values, touched, errors }) => {
  return (
    <>
      <TextField
        disabled
        variant="filled"
        type="text"
        label="Id"
        value={rowData.id}
        name="id"
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
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
        variant="filled"
        type="text"
        label="Email"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.email}
        name="email"
        error={!!touched.email && !!errors.email}
        helperText={touched.email && errors.email}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
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
        variant="filled"
        type="text"
        label="Contact First Name"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.contactFirstName}
        name="contactFirstName"
        error={!!touched.contactFirstName && !!errors.contactFirstName}
        helperText={touched.contactFirstName && errors.contactFirstName}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Phone"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.phone}
        name="phone"
        error={!!touched.phone && !!errors.phone}
        helperText={touched.phone && errors.phone}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Address Line 1"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.addressLine1}
        name="addressLine1"
        error={!!touched.addressLine1 && !!errors.addressLine1}
        helperText={touched.addressLine1 && errors.addressLine1}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Address Line 2"
        onBlur={handleBlur}
        onChange={handleChange}
        //value={values.addressLine2 == ! null ? rowData.addressLine2 : ""}
        value={values.addressLine2 ?? ""}
        name="addressLine2"
        error={!!touched.addressLine2 && !!errors.addressLine2}
        helperText={touched.addressLine2 && errors.addressLine2}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="City"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.city}
        name="city"
        error={!!touched.city && !!errors.city}
        helperText={touched.city && errors.city}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="State"
        onBlur={handleBlur}
        onChange={handleChange}
        //value={values.state == ! null ? rowData.state : ""}
        value={values.state ?? ""}
        name="state"
        error={!!touched.state && !!errors.state}
        helperText={touched.state && errors.state}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Postal Code"
        onBlur={handleBlur}
        onChange={handleChange}
        //value={values.postalCode == ! null ? rowData.postalCode : ""}
        value={values.postalCode ?? ""}
        name="postalCode"
        error={!!touched.postalCode && !!errors.postalCode}
        helperText={touched.postalCode && errors.postalCode}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Country"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.country}
        name="country"
        error={!!touched.country && !!errors.country}
        helperText={touched.country && errors.country}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Credit Limit"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.creditLimit}
        name="creditLimit"
        error={!!touched.creditLimit && !!errors.creditLimit}
        helperText={touched.creditLimit && errors.creditLimit}
        sx={{ gridColumn: "span 2" }}
      />
    </>
  );
}

export default CustomersFormEditInputs;
