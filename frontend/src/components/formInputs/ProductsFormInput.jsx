import { TextField, FormControl, InputLabel, Select, MenuItem } from "@mui/material";

const ProductsFormInputs = ({ rowData, handleBlur, handleChange, values,
  touched, errors, isEdit, setFieldValue, dataProductLine }) => {

  const isproductLineValid = dataProductLine
    ? dataProductLine.some((productLine) => productLine === values.productLine)
    : false;

  return (
    <>
      {isEdit && (
        <TextField
          disabled
          variant="filled"
          type="text"
          label="Id"
          value={values.id}
          name="id"
          sx={{ gridColumn: "span 1" }}
        />
      )}
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
        <InputLabel id="productLine-select-label">Product Line</InputLabel>
        <Select
          labelId="productLine-select-label"
          id="productLine-select"
          name="productLine"
          value={isproductLineValid ? values.productLine : ''}
          onChange={(event) => setFieldValue('productLine', event.target.value)}
          onBlur={handleBlur}
          label="Product Line"
        >
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
        label="Scale"
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
        label="Vendor"
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
        label="Description"
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
        label="Quantity"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.quantityInStock}
        name="quantityInStock"
        error={!!touched.quantityInStock && !!errors.quantityInStock}
        helperText={touched.quantityInStock && errors.quantityInStock}
        sx={{ gridColumn: "span 1" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="Buy Price"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.buyPrice}
        name="buyPrice"
        error={!!touched.buyPrice && !!errors.buyPrice}
        helperText={touched.buyPrice && errors.buyPrice}
        sx={{ gridColumn: "span 2" }}
      />
      <TextField
        variant="filled"
        type="text"
        label="MSRP"
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
