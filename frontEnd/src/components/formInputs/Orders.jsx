import { TextField, FormControl, InputLabel, Select, MenuItem, FormHelperText } from "@mui/material";

import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';
import Divider from '@mui/material/Divider';

const OrdersFormInputs = ({ handleBlur, handleChange, values, touched,
  errors, ordersSchema, setFieldValue, dataProductIdNameQuantityInStock,
  dataCustomersIdNameCreditLimit, handleInputChange
}) => {

  const tomorrow = dayjs().add(1, 'day');

  return (
    <>
      <Divider textAlign="left" sx={{ gridColumn: "span 4" }}>
        Order Informations
      </Divider>

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 2" }}
        validationschema={ordersSchema}
        error={!!touched.customerId && !!errors.customerId}
      >
        <InputLabel id="customerId-select-label">Customer Id</InputLabel>
        <Select
          labelId="customerId-select-label"
          id="customerId-select"
          name="customerId"
          value={values.customerId}
          onChange={(event) => setFieldValue('customerId',
            event.target.value)}
          onBlur={handleBlur}
          label="Customer Id"
        >
          {dataCustomersIdNameCreditLimit && dataCustomersIdNameCreditLimit.map((customer) => (
            <MenuItem key={customer.id} value={customer.id}>
              {customer.id} {" "}
              {customer.name} {" CREDIT LIMIT "} {customer.creditLimit}
            </MenuItem>
          ))}
        </Select>
        <FormHelperText error={!!touched.customerId && !!errors.customerId}>
          {touched.customerId && errors.customerId}
        </FormHelperText>
      </FormControl>

      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DemoContainer
          components={['DatePicker']}
          sx={{ gridColumn: "span 1" }}
        >
          <DatePicker
            label="Required Date"
            defaultValue={tomorrow}
            disablePast
          />
        </DemoContainer>
      </LocalizationProvider>

      <TextField
        variant="filled"
        type="text"
        label="Comments"
        multiline
        rows={3}
        onBlur={handleBlur}
        onChange={handleChange}
        values={values.comments}
        name="comments"
        error={!!touched.comments && !!errors.comments}
        helperText={touched.comments && errors.comments}
        sx={{ gridColumn: "span 1" }}
      />


      <Divider textAlign="left" sx={{ gridColumn: "span 4" }}>
        Order Itens
      </Divider>

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 2" }}
        validationschema={ordersSchema}
        error={!!touched.productId && !!errors.productId}
      >
        <InputLabel id="productId-select-label">Product Id</InputLabel>
        <Select
          labelId="productId-select-label"
          id="productId-select"
          name="productId"
          value={values.productId}
          onChange={(event) => setFieldValue('productId',
            event.target.value, handleInputChange("productId", event.target.value))}
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
        // onChange={handleChange}
        onChange={(event) => setFieldValue('quantityOrdered',
          event.target.value, handleInputChange("quantityOrdered", event.target.value))}
        value={values.quantityOrdered}
        name="quantityOrdered"
        error={!!touched.quantityOrdered && !!errors.quantityOrdered}
        helperText={touched.quantityOrdered && errors.quantityOrdered}
        sx={{ gridColumn: "span 1" }}
      />


    </>
  )
}

export default OrdersFormInputs;
