
import { TextField, FormControl, InputLabel, Select, MenuItem, FormHelperText } from "@mui/material";
import Divider from '@mui/material/Divider';

const OrdersDetailsFormInputs = ({ handleBlur, handleChange, values, touched,
  errors, ordersDetailsSchema, setFieldValue, dataProductIdNameQuantityInStock }) => {

  return (
    <>
      <Divider textAlign="left" sx={{ gridColumn: "span 5" }}>
        Order Itens
      </Divider>

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 2" }}
        validationschema={ordersDetailsSchema}
        error={!!touched.productId && !!errors.productId}
      >
        <InputLabel id="productId-select-label">Product Id</InputLabel>
        <Select
          labelId="productId-select-label"
          id="productId-select"
          name="productId"
          value={values.productId}
          onChange={(event) => setFieldValue("productId",
            event.target.value)}
          onBlur={handleBlur}
          label="Product Id"
        >
          {dataProductIdNameQuantityInStock && dataProductIdNameQuantityInStock.map((product) => (
            <MenuItem key={product.id} value={product.id}>
              {product.id} {" - "}
              {product.name} {" - stock - "} {product.quantityInStock}
            </MenuItem>
          ))}
        </Select>
        <FormHelperText error={!!touched.productId && !!errors.productId}>
          {touched.productId && errors.productId}
        </FormHelperText>
      </FormControl >

      <TextField
        variant="filled"
        type="text"
        label="Quantity"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.quantityOrdered}
        name="quantityOrdered"
        error={!!touched.quantityOrdered && !!errors.quantityOrdered}
        helperText={touched.quantityOrdered && errors.quantityOrdered}
        sx={{ gridColumn: "span 1" }}
      />
    </>
  )

}

export default OrdersDetailsFormInputs;
