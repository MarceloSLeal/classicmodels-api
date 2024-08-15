import { TextField } from "@mui/material";

const OfficesFormAddInputs = ({ handleBlur, handleChange, values, touched,
  errors }) => {

  return (
    <>
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
        label="State"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.state}
        name="state"
        error={!!touched.state && !!errors.state}
        helperText={touched.state && errors.state}
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
        label="Address1"
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
        label="Address2"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.addressLine2}
        name="addressLine2"
        error={!!touched.addressLine2 && !!errors.addressLine2}
        helperText={touched.addressLine2 && errors.addressLine2}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Postal Code"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.postalCode}
        name="postalCode"
        error={!!touched.postalCode && !!errors.postalCode}
        helperText={touched.postalCode && errors.postalCode}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Territory"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.territory}
        name="territory"
        error={!!touched.territory && !!errors.territory}
        helperText={touched.territory && errors.territory}
        sx={{ gridColumn: "span 2" }}
      />
    </>
  )

}

export default OfficesFormAddInputs;
