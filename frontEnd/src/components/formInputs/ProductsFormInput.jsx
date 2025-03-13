import { TextField, FormControl, InputLabel, Select, MenuItem } from "@mui/material";

const ProductsFormInputs = ({ rowData, handleBlur, handleChange, values,
  touched, errors, isEdit, setFieldValue, dataProductLine }) => {

  const isProductLineValid = dataProductLine
    ? dataProductLine.some((productline) => productline === values.productline)
    : false;

  return (
    <>
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

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 2" }}
      >
        <InputLabel id="productline-select-label">Product Line</InputLabel>
        <Select
          labelId="productline-select-label"
          id="productline-select"
          name="productline"
          value={isProductLineValid ? values.productLine : ""}
          onChange={(event) => setFieldValue('productline', event.target.value)}
          onBlur={handleBlur}
          label="Product Line"
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>

          {dataProductLine && dataProductLine.map((pline) => (
            <MenuItem key={pline} value={pline}>
              {pline}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <TextField
        variant="filled"
        type="text"
        label="scale"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.scale}
        name="scale"
        error={!!touched.scale && !!errors.scale}
        helperText={touched.scale && errors.scale}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="vendor"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.vendor}
        name="vendor"
        error={!!touched.vendor && !!errors.vendor}
        helperText={touched.vendor && errors.vendor}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="description"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.description}
        name="description"
        error={!!touched.description && !!errors.description}
        helperText={touched.description && errors.description}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="quantity"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.quantityInStock}
        name="quantity"
        error={!!touched.quantity && !!errors.quantity}
        helperText={touched.quantity && errors.quantity}
        sx={{ gridColumn: "span 1" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="buy price"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.buyPrice}
        name="buyprice"
        error={!!touched.buyprice && !!errors.buyprice}
        helperText={touched.buyprice && errors.buyprice}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="msrp"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.msrp}
        name="msrp"
        error={!!touched.msrp && !!errors.msrp}
        helperText={touched.msrp && errors.msrp}
        sx={{ gridColumn: "span 2" }}
      />
    </>
  );
}

export default ProductsFormInputs;
